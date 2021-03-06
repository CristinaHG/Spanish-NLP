import scala.collection.mutable
import scala.util.hashing.Hashing.Default

/**
  * Created by cris on 29/10/16.
  */
//A simple shallow parser using a Brill-based part-of-speech tagger.
//The given lexicon is a dictionary of known words and their part-of-speech tag.
//The given default tags are used for unknown words.
//Unknown words that start with a capital letter are tagged NNP (except for German).
//Unknown words that contain only digits and punctuation are tagged CD.
//  Optionally, morphological and contextual rules (or a language model) can be used
//  to improve the tags of unknown words.
class Parser(lex: String,morph: String, contx: String, lemma:String,private val default: List[String],enc:String,comm:String) {

  private val tokenizer = new Tokenizer
  private val lexicon = new Lexicon
  private val morphology = new Morphology
  private val context = new Context
  private val posTagger = new PosTagger
  private val lematizer = new Lemmatizer
  //set dictionary of verbs for lemmatize
  lematizer.setVerbsDict(lemma)
  //Load data
  if (!lex.isEmpty) lexicon.read(lex, enc, comm)
  if (!morph.isEmpty) morphology.read(morph, enc, comm)
  if (!contx.isEmpty) context.read(contx, enc, comm)

  // if(!model.isEmpty) apply Model



  // gets a message and a string of results to print
  // concats them and print them by command line
def commandLine(comments:String,moduleResult:String)={
    print(comments+moduleResult)
  }



  // a string (sentences) and returns a tagged Unicode string (TaggedString).
  //  Sentences in the output are separated by newlines.
  //With tokenize=True, punctuation is split from words and sentences are separated by \n.
  //With tags=True, part-of-speech tags are parsed (NN, VB, IN, ...).
  //With chunks=True, phrase chunk tags are parsed (NP, VP, PP, PNP, ...).
  //With relations=True, semantic role labels are parsed (SBJ, OBJ).
  //  With lemmata=True, word lemmata are parsed.
  //Optional parameters are passed to
  //the tokenizer, tagger, chunker, labeler and lemmatizer
  def parse(text: String, tokenize: Boolean, tags: Boolean, lemmatize: Boolean ,mapCall:(String,String)=>(String,String)): String = {

    if (tokenize == true || tags == true || lemmatize == true) {
      //Tagger (needed by chunker,labeler and lemmatizer)
      var tagged = List[List[(String, String)]]()
      //Tokens needed by tagger and lemmatizer
      var s = tokenizer.find_tokens(text)

      //lematizer and chunks need tags
      tagged = s.map(t => posTagger.find_tags(t, lexicon, morphology, context,default, mapCall))
      if (lemmatize == true) {
        var lemmatas = tagged.map(t => this.lematizer.get_lemmas(t)).toList
        // Collapse raw list.
        // Tags are separated by slashes.
        // Slashes in words are encoded with &slash;
        var SlashLemmata = for (l <- lemmatas; u <- l) yield (List(u._1.replaceAll("/", "&slash;"), u._2, u._3).mkString("/"))
        return SlashLemmata.mkString("\n")
      } else if (tags == true) {
        return tagged.mkString
        //else return tokens
      } else return s.mkString
      //if no options specified, return introduced text
    }else return text
  }

}
