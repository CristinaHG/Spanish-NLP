import scala.collection.mutable

/**
  * Created by cris on 27/08/16.
  */

object ScalaApp {

  //lexicon uses the Parole tagset
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


  def main(args: Array[String]) {
    val lemmatizr = new Lemmatizer
    val mappedVerbs = lemmatizr.verbsToDictionaryPair(lemmatizr.verbsDict)
    // print(mappedVerbs)
    //    print(lemmatizr.lista(1)._1)
    val lemas = lemmatizr.get_lemmas(lemmatizr.lista15, mappedVerbs)
    lemas.foreach(i => print(i))

    //test tokenizer
    val string1="Los gatos negros son horribles."
    val string2="no podrás vivir eternamente"
    val string3="Los ingenieros informáticos son muy inteligentes"
    val string4="Las atletas españolas son muy buenas deportistas"
    val string5="Alejandro quiere a Cristina más que a nada, como nunca antes quiso a nadie."
    val string6="Los lobos aullaban desesperadamente"
    val string7="Dijo que lo esperáramos aquí mientras compraba galletas"
    val string8="Te advertimos que serían duros"
    val string9="reconozco que me comporté mal"
    val string10="todo esfuerzo valdrá la pena"
    val string11="dejaron que murieran de hambre"
    val string12="nos atracaron a mano armada"
    val string13="cuando éramos niños repelíamos muchos bichos"
    val string14="con tanto calor se funde el hielo"
    val string15="lo que no queremos es que lo acaparéis todo"
    val toknzr=new Tokenizer
    toknzr.find_tokens("La verdad... es que no me parece normal.").foreach(t => print(t+"\n"))
    ///toknzr.find_tokens(string2).foreach(t => print(t+" "))
  }
}

