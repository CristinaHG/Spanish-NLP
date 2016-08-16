/**
  * Created by cris on 15/08/16.
  */
class Lemmatizer {
  //val ttokenandtag = List[List[String]]

  val lista = List(("Los", "DT"), ("gatos", "NNS"),("negros", "JJ"),("son", "VB"),("bonitos", "JJ"))

  def get_lemmas(l: List[(String,String)]) = {
    var word :String=""
    var lemma:String=""
    var pos:String=""
  l.foreach( i => {word=i._1;pos=i._2;lemma=i._1;} )
  }
}

object ScalaApp {
  def  main(args: Array[String]) {
 val lemmatizr=new Lemmatizer
    print(lemmatizr.lista(1)._1)
    print(lemmatizr.get_lemmas(lemmatizr.lista))

  }
}



