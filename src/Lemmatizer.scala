/**
  * Created by cris on 15/08/16.
  */
class Lemmatizer {
  //val ttokenandtag = List[List[String]]

  var lista = List(("Los", "DT"), ("gatos", "NNS"),("negros", "JJ"),("son", "VB"),("bonitos", "JJ"))

  def singularize(word: String, pos:String ):String={
    val w=word.toLowerCase()
    //los gatos=> el gato
    if (pos=="DT"){
      if(w.compareTo("la")==0 || w.compareTo("las")==0 || w.compareTo("los")==0)  "el"
      if(w.compareTo("una")==0 || w.compareTo("unas")==0 || w.compareTo("unos")==0) "un"
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

    return w
  }

  def get_lemmas(l: List[(String,String)]): List[(String, String)] = {
    var word :String=""
    var lemma:String=""
    var pos:String=""
  l.foreach( i => {word=i._1;pos=i._2;lemma=i._1;
  if (pos.startsWith("DT")) lemma=singularize(word,"DT")
//  else if (pos.startsWith("JJ")) lemma=predicative(word)
//  else if (pos.startsWith("NNS")) lemma=singularize(word)
//  else if (pos.startsWith("VB") || pos.startsWith("MD")) lemma= conjugate(word,INFINITIVE)
   i.+(lemma)
 })
    return l
  }






}

object ScalaApp {
  def  main(args: Array[String]) {
 val lemmatizr=new Lemmatizer
    print(lemmatizr.lista(1)._1)
    print(lemmatizr.get_lemmas(lemmatizr.lista))

  }
}



