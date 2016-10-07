import scala.collection.mutable

/**
  * Created by cris on 07/10/16.
  */
class PosTagger {

  //lexicon uses the Parole tagset
  // http://www.lsi.upc.edu/~nlp/SVMTool/parole.html
  // http://nlp.lsi.upc.edu/freeling/doc/tagsets/tagset-es.html

  val PAROLE="parole"
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

  // Converts a Parole tag to a Penn Treebank II tag. Ej: importantísimo/AQ => importantísimo/ADJ
  def parole2penntreebank(token:String,tag:String):(String, String)={
   return (token,parole.getOrElse(tag,tag))
  }
  // Returns a (token, tag)-tuple with a simplified universal part-of-speech tag.
  def penntreebank2universal(token: String, tag: String):(String,String) ={
    if (tag.startsWith("NNP-") || tag.startsWith("NNPS-")) return (token,(NOUN.concat(tag.split("-").toString.formatted("%s-%s"))))
      //return (token, "%s-%s" % (NOUN, tag.split("-")[-1]))
    if tag in ("NN", "NNS", "NNP", "NNPS", "NP"):
    return (token, NOUN)
    if tag in ("MD", "VB", "VBD", "VBG", "VBN", "VBP", "VBZ"):
    return (token, VERB)
    if tag in ("JJ", "JJR", "JJS"):
    return (token, ADJ)
    if tag in ("RB", "RBR", "RBS", "WRB"):
    return (token, ADV)
    if tag in ("PRP", "PRP$", "WP", "WP$"):
    return (token, PRON)
    if tag in ("DT", "PDT", "WDT", "EX"):
    return (token, DET)
    if tag in ("IN",):
    return (token, PREP)
    if tag in ("CD",):
    return (token, NUM)
    if tag in ("CC",):
    return (token, CONJ)
    if tag in ("UH",):
    return (token, INTJ)
    if tag in ("POS", "RP", "TO"):
    return (token, PRT)
    if tag in ("SYM", "LS", ".", "!", "?", ",", ":", "(", ")", "\"", "#", "$"):
    return (token, PUNC)
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

}
