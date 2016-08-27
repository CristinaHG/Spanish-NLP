/**
  * Created by cris on 27/08/16.
  */

object ScalaApp {


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
    val toknzr=new Tokenizer
    toknzr.find_tokens(string1).foreach(t => print(t))
  }
}


var lista5=List(("Alejandro","NNP"),("quiere","VB"),("a","IN"),("Cristina","NNP"),("más","RB"),("que","IN"),("a","IN"),("nada","DT"),
  (",",","),("como","IN"),("nunca","RB"),("antes","IN"),("quiso","VB"),("a","IN"),("nadie","DT"),(".","."))
var lista6=List(("Los","DT"),("lobos","NNS"),("aullaban","VB"),("desesperadamente","NN"))
var lista7=List(("Dijo","VB"),("que","IN"),("lo","DT"),("esperáramos","VB"),("aquí","IN"),("mientras","IN"),("compraba","VB"),("galletas","NNS"))
var lista8=List(("Te","PRP"),("advertimos","VB"),("que","WP"),("serían","VB"),("duros","JJ"))
var lista9=List(("reconozco","VB"),("que","WP"),("me","PRP"),("comporté","VB"),("mal","RB"))
var lista10=List(("todo","DT"),("esfuerzo","NN"),("valdrá","VB"),("la","DT"),("pena","NN"))
var lista11=List(("dejaron","VB"),("que","IN"),("murieran","VB"),("de","IN"),("hambre","NN"))
var lista12=List(("nos","PRP"),("atracaron","VB"),("a","IN"),("mano","NN"),("armada","VBN"))
var lista13=List(("cuando","IN"),("éramos","VB"),("niños","NNS"),("repelíamos","VB"),("muchos","RB"),("bichos","NNS"))
var lista14=List(("con","IN"),("tanto","RB"),("calor","NN"),("se","PRP"),("funde","VB"),("el","DT"),("hielo","NN"))
var lista15=List(("lo","DT"),("que","WP"),("no","RB"),("queremos","VB"),("es","VB"),("que","IN"),("lo","DT"),("acaparéis","VB"),
  ("todo","DT"))
