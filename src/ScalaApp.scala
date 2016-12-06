import scala.collection.mutable

/**
  * Created by cris on 27/08/16.
  */

object ScalaApp {



  def main(args: Array[String]) {
    //val lemmatizr = new Lemmatizer
//    val mappedVerbs = lemmatizr.verbsToDictionaryPair(lemmatizr.verbsDict)
//    // print(mappedVerbs)
//    //    print(lemmatizr.lista(1)._1)
//    val lemas = lemmatizr.get_lemmas(lemmatizr.lista15, mappedVerbs)
//    lemas.foreach(i => print(i))

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
    val tokens=toknzr.find_tokens(string1)
    var lista:List[Array[String]]=Nil
    lista::=Array("Los","gatos","negros","son","horribles",".",".")
    var nuevo=tokens.exists(t=> lista.toList.exists(a=> a==t))
    //.foreach(t => print(t+"\n"))
    ///toknzr.find_tokens(string2).foreach(t => print(t+" "))

    val myLexicon=new Lexicon
    val myMorphology=new Morphology
    val myContext=new Context
    //val verbs= scala.io.Source.fromFile("../Spanish_Lematizer/src/es-morphology.txt").getLines()
    val lexiconSpanish=myLexicon.read("../Spanish_Lematizer/data/es-lexicon.txt","utf-8",";;;")
    val morphologySpanish=myMorphology.read("../Spanish_Lematizer/data/es-morphology.txt","utf-8",";;;")
    val contextSpanish=myContext.read("../Spanish_Lematizer/data/es-context.txt","utf-8",";;;")
    val posTagged=new PosTagger
    //posTagged.find_tags(tokens,myLexicon,"",myMorphology,myContext,"",List("NN","NNP","CD"),posTagged.parole2penntreebank)
    val lexiconPath="../Spanish_Lematizer/data/es-lexicon.txt"
    val morphologyPath="../Spanish_Lematizer/data/es-morphology.txt"
    val contextPath="../Spanish_Lematizer/data/es-context.txt"
    val verbsPath="/home/cris/mrcrstnherediagmez@gmail.com/Spanish_Lematizer/data/es-verbs.txt"
    val myParser=new Parser(lexiconPath,"",morphologyPath,contextPath,verbsPath,List("NCS","NP","Z"),"utf-8",";;;")
    myParser.parse("el gato negro.",true,true,true, posTagged.parole2penntreebank)
    myParser.parse("el sedentarismo físico es la carencia de actividad física fuerte. Yo no hago deporte.",true,true,true,posTagged.parole2penntreebank)
    myParser.parse("El sedentarismo físico se presenta con mayor frecuencia en la vida moderna urbana, en sociedades altamente tecnificadas en donde todo está pensado para evitar grandes esfuerzos físicos, en las clases altas y en los círculos intelectuales en donde las personas se dedican más a actividades cerebrales.",true,true,true,
      posTagged.parole2penntreebank)
  }
}

