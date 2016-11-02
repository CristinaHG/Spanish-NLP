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
class Parser(lex: String,model: String,morph: String, contx: String, omission: List[String],enc:String,comm:String) {

  val tokenizer= new Tokenizer
  val lexicon=new Lexicon
  val morphology=new Morphology
  val context=new Context
  val default=omission
  val posTagger=new PosTagger
  val lematizer=new Lemmatizer
  //Load data
  if(!lex.isEmpty) lexicon.read(lex,enc,comm)
  if(!morph.isEmpty) morphology.read(morph,enc,comm)
  if(!contx.isEmpty) context.read(contx,enc,comm)
  // if(!model.isEmpty) apply Model



  // a string (sentences) and returns a tagged Unicode string (TaggedString).
  //  Sentences in the output are separated by newlines.
  //With tokenize=True, punctuation is split from words and sentences are separated by \n.
  //With tags=True, part-of-speech tags are parsed (NN, VB, IN, ...).
  //With chunks=True, phrase chunk tags are parsed (NP, VP, PP, PNP, ...).
  //With relations=True, semantic role labels are parsed (SBJ, OBJ).
  //  With lemmata=True, word lemmata are parsed.
  //Optional parameters are passed to
  //the tokenizer, tagger, chunker, labeler and lemmatizer
  def parse(text:String,tokenize:Boolean,tags:Boolean,chunks:Boolean,lemmatize:Boolean): Unit ={
  //Tokenizer
    //if(tokenize==true){
      var s=tokenizer.find_tokens(text).map(t=>t.split(" ").toList)
      var tagged=mutable.MutableList[List[(String,String)]]()
    var lemmatas=List()
    //}
    //tagger (needed by chunker,labeler and lemmatizer)
    if(tags==true || chunks==true || lemmatize==true){
      //lematizer and chunks need tags
       tagged=s.map(t=>posTagger.find_tags(t,lexicon,model,morphology,context,"",default,posTagger.parole2penntreebank))
      if(lemmatize==true){
        lemmatas=tagged.map(t=>this.lematizer.find_lemma())
      }
    }

  }
 }
