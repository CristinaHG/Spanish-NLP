/**
  * Created by cris on 15/08/16.
  */
import scala.io.Source

class Lemmatizer {


  var lista = List(("Los", "DT"), ("gatos", "NNS"),("negros", "JJ"),("son", "VB"),("horribles", "JJ"))
  val verbsDict="/home/cris/mrcrstnherediagmez@gmail.com/Spanish_Lematizer/src/es-verbs.txt"

  //-----------load file and make a dictionary pair[verb form, infinitive] method

  def verbsToDictionaryPair(file: String):Map[String,String]={
    var dict:Map[String,String]=Map()
    for (line<-Source.fromFile(file).getLines().filterNot(_.startsWith(";"))){ //not filter comments
      val a=line.split(",")
      //dict+=("comíamos"->"comer")
      a.foreach(u=>dict+=(u->a{0}))
    }
    return dict
  }

  //-----returns the infinitive form of the given verb or none
  def lemma(verb:String,dictionary:Map[String,String]):String={
  if(dictionary.getOrElse(verb," ")!="") return dictionary(verb).toLowerCase
  else return find_lemma(verb)
  }


  //create dictionary method
//def toDictionaryPair():Map[String, String]={
//  var dict:Map[String,String]=Map()
//
//}


  //----------singularize method
  def singularize(word: String, pos:String ):String={
    val w=word.toLowerCase()
    //los gatos=> el gato
    if (pos=="DT"){
      if(w.compareTo("la")==0 || w.compareTo("las")==0 || w.compareTo("los")==0) return "el"
      if(w.compareTo("una")==0 || w.compareTo("unas")==0 || w.compareTo("unos")==0) return "un"
    }
    //hombres=>hombre
    if (w.endsWith("es") && (w.substring(0,w.length-2).endsWith("br") || (w.substring(0,w.length-2).endsWith("i")) ||
      (w.substring(0,w.length-2).endsWith("j")) || (w.substring(0,w.length-2).endsWith("t")) || (w.substring(0,w.length-2).endsWith("zn"))))
      return w.substring(0,w.length-1)
    //gestiones=>gestión
    val endings=List(("anes", "án"),
      ("enes", "én"),
      ("eses", "és"),
      ("ines", "ín"),
      ("ones", "ón"),
      ("unes", "ún"))
    endings.foreach(e=> {if(w.endsWith(e._1)) return w.substring(0,w.length-4).concat(e._2)})
    //hipotesis=>hipotesis
    if (w.endsWith("esis") || w.endsWith("osis") || w.endsWith("isis")) return w
    //luces=>luz
    if(w.endsWith("ces")) return w.substring(0,w.length-3).concat("z")
    //hospitales=>hospital
    if (w.endsWith("es")) return w.substring(0,w.length-2)
    //gatos=>gato
    if (w.endsWith("s")) return w.substring(0,w.length-1)

   else return w
  }

  //--------isVowel
  def isVowel(char: Char):Boolean={
   if (char.equals("a","e","i","o","u"))
    return true
    else return false
  }

  //---------normalize
  def normalize(char: Char):Char={
    char match {
      case 'á' => 'a'
      case 'é' => 'e'
      case 'í'=> 'i'
      case 'ó' => 'o'
      case 'ú' => 'u'
      case _ => char
    }
  }

  //---------------predicative method

  def predicative(word:String):String={
  var w=word.toLowerCase()
    //histéricos=>histérico
  if (w.endsWith("os") || w.endsWith("as")) return w.substring(0,w.length-1)
    // histérico=>histérico
    if (w.endsWith("o")) return w
    //histérica=>histérico
    if(w.endsWith("a")) return w.substring(0,w.length-1).concat("o")
    //horribles=>horrible, humorales=>humoral
    if(w.endsWith("es")) {
      if (w.length >= 4 && !(isVowel(normalize(w.charAt(w.length - 3)))) && !isVowel(normalize(w.charAt(w.length - 4)))) return w.substring(0, w.length - 1)
    }else return w.substring(0,w.length-2)
    return w
  }

  //----------conjugate verb to infinitive form
//  def toInfinitive(verb:String):String={
//
//  }

  def get_lemmas(l: List[(String,String)]): List[(String, String,String)] = {
    var word :String=""
    var lemma:String=""
    var pos:String=""
    var lemmalist: List[(String, String, String)] = List()

  l.foreach( i => {word=i._1;pos=i._2;lemma=i._1;
  if (pos.startsWith("DT")) lemma=singularize(word,"DT")
  if (pos.startsWith("JJ")) lemma=predicative(word)
  if (pos.startsWith("NNS")) lemma=singularize(word,"NNS")
    if (pos.startsWith("VB") || pos.startsWith("MD")) //lemma= toInfinitive(word)
    lemmalist= (word,pos,lemma)::lemmalist
 })
    return lemmalist.reverse
  }







}

object ScalaApp {
  def  main(args: Array[String]) {
 val lemmatizr=new Lemmatizer
//    print(lemmatizr.lista(1)._1)
//    val lemas=lemmatizr.get_lemmas(lemmatizr.lista)
//    lemas.foreach(i=>print(i))
    val mappedVerbs=lemmatizr.verbsToDictionaryPair(lemmatizr.verbsDict)
    print(mappedVerbs)
  }
}



