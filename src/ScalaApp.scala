import scala.collection.mutable

/**
  * Created by cris on 9/12/16.
  */

object ScalaApp {



  def main(args: Array[String]) {
    //files path
    val lexiconPath="../Spanish_Lematizer/data/es-lexicon.txt"
    val morphologyPath="../Spanish_Lematizer/data/es-morphology.txt"
    val contextPath="../Spanish_Lematizer/data/es-context.txt"
    val verbsPath="../Spanish_Lematizer/data/es-verbs.txt"
    val tagger=new PosTagger
    val myParser=new Parser(lexiconPath,morphologyPath,contextPath,verbsPath,List("NCS","NP","Z"),"utf-8",";;;")
    //get input from user
    print("Introduce el texto que desees parsear:")
    var txt=scala.io.StdIn.readLine().toString
    //txt="\""+txt+"\""
    print("Introduce las opciones que deseas aplicar, separadas por comas:")
    var options=scala.io.StdIn.readLine().split(',').map(t=>t.toBoolean)
    print("¿Qué tagset deseas usar? 1:parole 2:penn treebank 3:universal")
    val tagset=scala.io.StdIn.readInt()
    val tagsetFunc:(String, String) => (String, String)= tagset match{
      case 1 => null //is parole by default
      case 2 => tagger.parole2penntreebank
      case 3 => tagger.parole2universal
    }


    //myParser.commandLine("Texto parseado: ",myParser.parse("el gato negro.",true,true,true, tagger.parole2penntreebank))
    myParser.commandLine("Texto parseado: ",myParser.parse(txt,options{0},options{1},options{2},tagsetFunc))
  }
}

