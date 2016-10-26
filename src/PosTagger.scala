import scala.collection.mutable

/**
  * Created by cris on 07/10/16.
  */
class PosTagger {

  //lexicon uses the Parole tagset
  // http://www.lsi.upc.edu/~nlp/SVMTool/parole.html
  // http://nlp.lsi.upc.edu/freeling/doc/tagsets/tagset-es.html
  val lexicon=""
  val morphology=""
  val context=""
  var tagset="parole"
  //val PAROLE="parole"

  //Unknow words are recognized as numbers if they contain only digits and -,.:/%$
  val CD = """^[0-9\-\,\.\:\/\%\$]+$"""

  val parole=mutable.Map[String,String]()
  parole+=("AO"->"JJ") //primera
  parole+=("AQ"-> "JJ") //absurdo
  parole+=("CC"->"CC") //e
  parole+=("CS"-> "IN") //porque
  parole+=("DA"->"DT") //el
  parole+=("DD"->"DT") //ese
  parole+=("DI"->"DT") //mucha
  parole+=("DP"-> "PRP$") //mi,nuestra
  parole+=("DT"->"DT") //cuántos
  parole+=("Fa"-> ".") // !
  parole+=("Fc"->",") // ,
  parole+=("Fd"->":") // :
  parole+=("Fe"->"\"") // "
  parole+=("Fg"->".") // -
  parole+=("Fh"-> ".") //  /
  parole+=("Fi"-> ".") // ?
  parole+=("Fp"-> ".") // .
  parole+=("Fr"->".") // >>
  parole+=("Fs"->".") // ...
  parole+=("Fpa"-> "(") // (
  parole+=("Fpt"->")") // )
  parole+=("Fx"-> ".") // ;
  parole+=("Fz"-> ".") //
  parole+=("I"-> "UH") // ehm
  parole+=("NC"-> "NN") // islam
  parole+=("NCS"-> "NN") //guitarra
  parole+=("NCP"-> "NNS") // guitarras
  parole+=("NP"->"NNP") // Óscar
  parole+=("P0"->"PRP")  // se
  parole+=("PD"->"DT") // ése
  parole+=("PI"-> "DT") // uno
  parole+=("PP"->"PRP") // vos
  parole+=("PR"-> "WP$") // qué
  parole+=("PT"-> "WP$") // qué
  parole+=("PX"->"PRP$") // mío
  parole+=("RG"->"RB")  // tecnológicamente
  parole+=("RN"-> "RB") // no
  parole+=("SP"->"IN") // por
  parole+=("VAG"-> "VBG") // habiendo
  parole+=("VAI"->"MD")   // había
  parole+=("VAN"-> "MD") // haber
  parole+=("VAS"-> "MD") // haya
  parole+=("VMG"->"VBG")  // habiendo
  parole+=("VMI"->"VB") // habemos
  parole+=("VMM"->"VB")  // compare
  parole+=("VMN"-> "VB") // comparecer
  parole+=("VMP"->"VBN")  // comparando
  parole+=("VMS"->"VB")  // compararan
  parole+=("VSG"-> "VBG")  // comparando
  parole+=("VSI"-> "VB")   // será
  parole+=("VSN"-> "VB")   // ser
  parole+=("VSP"-> "VBN") // sido
  parole+=("VSS"-> "VB")   // sea
  parole+=("W"-> "NN") // septiembre
  parole+=("Z"-> "CD")  // 1,7
  parole+=("Zd"-> "CD")   // 1,7
  parole+=("Zm"-> "CD")   // £1,7
  parole+=("Zp"-> "CD")   // 1,7%


  val UNIVERSAL = "universal"

  val NOUN="NN"
  val VERB="VB"
  val ADJ="JJ"
  val ADV="RB"
  val PRON="PR"
  val DET="DT"
  val PREP="PP"
  val ADP="PP"
  val NUM="NO"
  val CONJ="CJ"
  val INTJ="UH"
  val PRT="PT"
  val PUNC="."
  val X = "X"


  //set tagset val
  def setTagset(tagsetname:String){
    this.tagset=tagsetname
  }

  // Converts a Parole tag to a Penn Treebank II tag. Ej: importantísimo/AQ => importantísimo/ADJ
  def parole2penntreebank(token:String,tag:String):(String, String)={
   return (token,parole.getOrElse(tag,tag))
  }
  // Returns a (token, tag)-tuple with a simplified universal part-of-speech tag.
  def penntreebank2universal(token: String, tag: String):(String,String) ={
    if (tag.startsWith("NNP-") || tag.startsWith("NNPS-")) return (token,(NOUN.concat(tag.split("-").toString.formatted("%s-%s"))))
      //return (token, "%s-%s" % (NOUN, tag.split("-")[-1]))
    if(List("NN", "NNS", "NNP", "NNPS", "NP").contains(tag)) return (token, NOUN)
    if(List("MD", "VB", "VBD", "VBG", "VBN", "VBP", "VBZ").contains(tag)) return (token, VERB)
    if(List("JJ", "JJR", "JJS").contains(tag)) return (token, ADJ)
    if(List("RB", "RBR", "RBS", "WRB").contains(tag)) return (token, ADV)
    if(List("PRP", "PRP$", "WP", "WP$").contains(tag)) return (token, PRON)
    if(List("DT", "PDT", "WDT", "EX").contains(tag)) return (token, DET)
    if(List("IN").contains(tag)) return (token, PREP)
    if(List("CD").contains(tag)) return (token, NUM)
    if(List("CC").contains(tag)) return (token, CONJ)
    if(List("UH").contains(tag)) return (token, INTJ)
    if(List("POS", "RP", "TO").contains(tag)) return (token, PRT)
    if(List("SYM", "LS", ".", "!", "?", ",", ":", "(", ")", "\"", "#", "$").contains(tag)) return (token, PUNC)
    return (token, X)
  }

  //Converts a Parole tag to a universal tag.
  def parole2universal(token:String, tag:String):(String,String)= {

    if(tag == "CS") return (token, CONJ)
    if(tag == "DP") return (token, DET)
    if(List("P0", "PD", "PI", "PP", "PR", "PT", "PX").contains(tag)) return (token, PRON)

    var paroletreebank=parole2penntreebank(token, tag)
    return penntreebank2universal(paroletreebank._1,paroletreebank._2)
  }

  //depending on tagset use tag func



  // Returns a list of (token, tag)-items for the given list of tokens:
  //  ["The", "cat", "purs"] => [("El", "DT"), ("gato", "NN"), ("ronronea", "VB")]
  //Words are tagged using the given lexicon of (word, tag)-items.
  //  Unknown words are tagged NN by default.
  //  Unknown words that start with a capital letter are tagged NNP (unless language="de").
  //  Unknown words that consist only of digits and punctuation marks are tagged CD.
  //  Unknown words are then improved with morphological rules.
  //All words are improved with contextual rules.
  //If a model is given, uses model for unknown words instead of morphology and context.
  //  If map is a function, it is applied to each (token, tag) after applying all rules.
//  def find_tags(tokens:List[String], lexicon:Lexicon, model:String, morphology:Morphology, context:String, entities:String, default:List[String],
//                mapCall:(String,String)=>(String,String)):List[(String,String)]={
//    var tagged:List[(String,String)]=Nil
//    // Tag known words.
//    tokens.foreach(t=> tagged::=(t,lexicon.lexiconDict.getOrElse(t,lexicon.lexiconDict.getOrElse(t.toLowerCase,"None"))))
//    //Tag unknow words
//    tagged.map( t=> {
//      var prev = ("None", "None")
//      var next = ("None", "None")
//      if (tagged.indexOf(t)>0) prev=tagged(tagged.indexOf(t)-1)
//      if (tagged.indexOf(t)<(tagged.length-1)) next=tagged(tagged.indexOf(t)+1)
//      if(t._2=="None"){
//        //use language model
//      //  if(model.compareTo("None")==false) entrenar usando modelo
//        //use NNP for capitalized words
//        if( t._1.matches("""^[A-Z][a-z]+.$""")) (t._1,default(1))
//          //use CD for digits and numbers
//        else if(t._1.matches(CD)) (t._1,default(2))
//          //use suffix rules (ej, -mente=ADV)
//        else if(!morphology.morphologyList.isEmpty){
//          (t._1, morphology.apply(t._1,default(0),prev,next,morphology.morphologyList,lexicon.lexiconDict))
//          // Use most frequent tag (NN).
//        }else{
//          (t._1,default(0))
//        }
//      }
//    })
//    //Tag words by context
//  }


}
