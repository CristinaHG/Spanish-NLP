/**
  * Created by cris on 15/08/16.
  */
import java.nio.file.{Files, Paths}

import org.scalatest._
import org.junit.runner.RunWith

import scala.collection.mutable
//import org.scalatest.junit.JUnitRunner
import org.junit.Test
import org.junit.Assert.assertArrayEquals


 class AppTest extends FunSuite {


   // Strings to be used to test
   val stringAbbr="Srta. y Sr."
   val string1 = "Los gatos negros son bonitos."
   val string2 = "Nadie podrá vivir eternamente"
   val string3 = "Los ingenieros informáticos son muy inteligentes"
   val string4 = "Aún no puedo creer que no asistiera a la fiesta."
   val string5 = "El cielo es azul. El agua es transparente y las amapolas son rojas."
   val string6 = "Los lobos aullaban desesperadamente"
   val string7 = "Dijo que lo esperáramos aquí mientras compraba galletas"
   val string8 = "Te advertimos que serían duros contigo"
   val string9 = "reconozco que me comporté mal"
   val string10 = "todo esfuerzo valdrá la pena"
   val string11 = "dejaron que murieran de hambre"
   val string12 = "nos atracaron a mano armada"
   val string13 = "cuando éramos niños cazábamos muchos bichos"
   val string14 = "con tanto calor se funde el hielo"
   val string15 = "El sedentarismo físico se presenta con mayor frecuencia en la vida moderna urbana, en sociedades altamente tecnificadas en donde todo está pensado para evitar grandes esfuerzos físicos, en las clases altas y en los círculos intelectuales en donde las personas se dedican más a actividades cerebrales."
   //needed paths
   val verbsPath = "/home/cris/mrcrstnherediagmez@gmail.com/Spanish_Lematizer/data/es-verbs.txt"
   val lexiconPath="../Spanish_Lematizer/data/es-lexicon.txt"
   val morphologyPath="../Spanish_Lematizer/data/es-morphology.txt"
   val contextPath="../Spanish_Lematizer/data/es-context.txt"
   //needed objects
   val myParser=new Parser(lexiconPath,morphologyPath,contextPath,verbsPath,List("NC","NP","Z"),"utf-8",";;;")
   val tokenizer = new Tokenizer
   val tagger = new PosTagger
   val lemmatizer = new Lemmatizer
   lemmatizer.setVerbsDict(verbsPath) //set lemmatizer dict
   val Lexicon= new Lexicon
   val context=new Context
   val morphology=new Morphology

   /**
     * Testing Tokenizer
     */

   //testing find tokens method
   test("find tokens test") {
     assert(tokenizer.find_tokens(stringAbbr) == List(List("Srta.",  "y", "Sr.")))
     assert(tokenizer.find_tokens(string1) == List(List("Los", "gatos", "negros", "son", "bonitos", ".")))
     assert(tokenizer.find_tokens(string2) == List(List("Nadie", "podrá", "vivir", "eternamente")))
     assert(tokenizer.find_tokens(string3) == List(List("Los", "ingenieros", "informáticos", "son", "muy", "inteligentes")))
     assert(tokenizer.find_tokens(string4) == List(List("Aún", "no", "puedo", "creer", "que", "no", "asistiera", "a", "la", "fiesta", ".")))
     assert(tokenizer.find_tokens(string5) == List(List("El", "cielo", "es", "azul", "."), List("El", "agua", "es", "transparente", "y", "las", "amapolas", "son", "rojas", ".")))
     assert(tokenizer.find_tokens(string6) == List(List("Los", "lobos", "aullaban", "desesperadamente")))
     assert(tokenizer.find_tokens(string7) == List(List("Dijo", "que", "lo", "esperáramos", "aquí", "mientras", "compraba", "galletas")))
     assert(tokenizer.find_tokens(string8) == List(List("Te", "advertimos", "que", "serían", "duros", "contigo")))
     assert(tokenizer.find_tokens(string9) == List(List("reconozco", "que", "me", "comporté", "mal")))
     assert(tokenizer.find_tokens(string10) == List(List("todo", "esfuerzo", "valdrá", "la", "pena")))
     assert(tokenizer.find_tokens(string11) == List(List("dejaron", "que", "murieran", "de", "hambre")))
     assert(tokenizer.find_tokens(string12) == List(List("nos", "atracaron", "a", "mano", "armada")))
     assert(tokenizer.find_tokens(string13) == List(List("cuando", "éramos", "niños", "cazábamos", "muchos", "bichos")))
     assert(tokenizer.find_tokens(string14) == List(List("con", "tanto", "calor", "se", "funde", "el", "hielo")))
     assert(tokenizer.find_tokens(string15) == List(List("El", "sedentarismo", "físico", "se", "presenta", "con", "mayor", "frecuencia", "en", "la", "vida", "moderna", "urbana", ",", "en", "sociedades", "altamente", "tecnificadas", "en", "donde", "todo", "está", "pensado", "para", "evitar", "grandes", "esfuerzos", "físicos", ",", "en", "las", "clases", "altas", "y", "en", "los", "círculos", "intelectuales", "en", "donde", "las", "personas", "se", "dedican", "más", "a", "actividades", "cerebrales", "."
     )))
   }

   test(" count sentences test") {
     assert(tokenizer.count_sentences(tokenizer.find_tokens(stringAbbr)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string1)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string2)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string3)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string4)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string5)) == 2)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string6)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string7)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string8)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string9)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string10)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string11)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string12)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string13)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string14)) == 1)
     assert(tokenizer.count_sentences(tokenizer.find_tokens(string15)) == 1)
   }


   /**
     * Testing POSTagger
     */

   //mapping function from parole tagset to penntreebank tagset
   test("parole to penntreebank tag test") {
     assert(tagger.parole2penntreebank("El", "DA") == ("El", "DT"))
     assert(tagger.parole2penntreebank("pájaro", "NC") == ("pájaro", "NN"))
     assert(tagger.parole2penntreebank("está", "VMI") == ("está", "VB"))
     assert(tagger.parole2penntreebank("en", "SP") == ("en", "IN"))
     assert(tagger.parole2penntreebank("la", "DA") == ("la", "DT"))
     assert(tagger.parole2penntreebank("jaula", "NC") == ("jaula", "NN"))
     assert(tagger.parole2penntreebank(".", "Fp") == (".", "."))
   }
   //mapping function from penntreebank tagset to universal tagset
   test("pentreebank to universal test") {
     assert(tagger.penntreebank2universal("El", "DT") == ("El", "DT"))
     assert(tagger.penntreebank2universal("pájaro", "X") == ("pájaro", "X"))
     assert(tagger.parole2penntreebank("está", "VB") == ("está", "VB"))
     assert(tagger.parole2penntreebank("en", "IN") == ("en", "IN"))
     assert(tagger.parole2penntreebank("la", "DT") == ("la", "DT"))
     assert(tagger.parole2penntreebank("jaula", "NC") == ("jaula", "NN"))
     assert(tagger.parole2penntreebank(".", "Fp") == (".", "."))
   }
   //mapping function from parole tagset to universal tagset
   test("parole to universal") {
     assert(tagger.parole2universal("El", "DA") == ("El", "DT"))
     assert(tagger.penntreebank2universal("pájaro", "NC") == ("pájaro", "X"))
     assert(tagger.parole2penntreebank("está", "VMI") == ("está", "VB"))
     assert(tagger.parole2penntreebank("en", "SP") == ("en", "IN"))
     assert(tagger.parole2penntreebank("la", "DA") == ("la", "DT"))
     assert(tagger.parole2penntreebank("jaula", "NC") == ("jaula", "NN"))
     assert(tagger.parole2penntreebank(".", "Fp") == (".", "."))
   }


   // Assert the accuracy of the Spanish tagger.
   test("test find tags whole corpus"){
     var wikicorpus=List[List[String]]()
     var i=0
     var n=0

     val lexiconSpanish=Lexicon.read("../Spanish_Lematizer/data/es-lexicon.txt","utf-8",";;;")
     val morphologySpanish=morphology.read("../Spanish_Lematizer/data/es-morphology.txt","utf-8",";;;")
     val contextSpanish=context.read("../Spanish_Lematizer/data/es-context.txt","utf-8",";;;")

     scala.io.Source.fromFile("../Spanish_Lematizer/corpus/tagged-es-wikicorpus.txt").getLines().foreach(line => wikicorpus ::= line.split(" ").toList)
     wikicorpus=wikicorpus.reverse
     wikicorpus.foreach(sentenceList=>{
       var s1= sentenceList.map(f=>f.split("/"))
       var s1ToTag=List[String]()
       s1.foreach(f=> s1ToTag::=f(0))
       s1ToTag=s1ToTag.reverse
       val tagged=tagger.find_tags(s1ToTag, Lexicon, morphology,context, List("NC","NP","Z"), null)

       var j=0
       s1.foreach(s=>{
         if(s(1)==tagged(j)._2) i=i+1
         n=n+1
         j=j+1
       })
     })
     assert(i.toFloat/n>0.91)
     print("accuracy tagger:" + i.toFloat/n)
   }


   /**
     * Testing Lemmatizer
     */

   //test accuracy of singularize method
   test("singularize") {
     var wordforms = List[List[String]]()
     var testDict = mutable.Map.empty[String, List[String]]
     //read file
     scala.io.Source.fromFile("../Spanish_Lematizer/test/data/wordforms-es-davies.csv").getLines().foreach(line => wordforms ::= line.split(" ").toList)
     wordforms.reverse.foreach(l => {
       val splitted = l(0).split(",").map(u => u.tail.stripPrefix("\"").stripSuffix("\""))
       val w = splitted(0)
       val lemma = splitted(1)
       val tag = splitted(2)
       val f = splitted(3)

       if (tag == "n") {
         testDict += (lemma -> (w +: testDict.getOrElse(lemma, List.empty)))
       }
     })

     var i = 0
     var n = 0
     testDict.foreach(f => {
       if (lemmatizer.singularize((f._2.sortWith(_.length < _.length)).head, "NN") == f._1) i = i + 1
       n = n + 1
     })
     assert(i.toFloat / n > 0.93)
     print("singularize accuracy:" + i.toFloat/n )
   }

   //test accuracy of predicative method
   test("predicative") {
     var wordforms = List[List[String]]()
     var testDict = mutable.Map.empty[String, List[String]]
     //read file
     scala.io.Source.fromFile("../Spanish_Lematizer/test/data/wordforms-es-davies.csv").getLines().foreach(line => wordforms ::= line.split(" ").toList)
     wordforms.reverse.foreach(l => {
       val splitted = l(0).split(",").map(u => u.tail.stripPrefix("\"").stripSuffix("\""))
       val w = splitted(0)
       val lemma = splitted(1)
       val tag = splitted(2)
       val f = splitted(3)

       if (tag == "j") {
         testDict += (lemma -> (w +: testDict.getOrElse(lemma, List.empty)))
       }
     })
     var i = 0
     var n = 0
     testDict.foreach(f => {
       if (lemmatizer.predicative(f._2.sortWith(_.length < _.length).head) == f._1) i = i + 1
       n = n + 1
     })
     assert(i.toFloat / n > 0.92)
     print("predicative accuracy:" + i.toFloat/n  )
   }

   //test accuracy of find lemma method
   test("find lemma") {
     var i = 0
     var n = 0
     var faults = List[(String, String)]()
     lemmatizer.getVerbsDict.foreach(u => {
       if (lemmatizer.find_lemma(u._1) == u._2) i = i + 1 else faults ::= u
       n = n + 1
     })

      assert(i.toFloat/n > 0.80)
      print("accuracy lematization of verbs: " + i.toFloat/n )
}

   //test get lemmas general example
   test("test get lemmas"){
     val lemmatas=List(("Los", "DT", "el"),
     ("gatos", "NNS", "gato"),
       ("negros", "JJ", "negro"),
     ("se", "PRP", "se"),
     ("sentó", "VB", "sentar"),
     ("en", "IN", "en"),
     ("la", "DT", "el"),
     ("alfombra", "NN", "alfombra"))

     assert(lemmatizer.get_lemmas(List(("Los", "DT"), ("gatos", "NNS"), ("negros", "JJ"), ("se", "PRP"), ("sentó", "VB"),
       ("en", "IN"), ("la", "DT"), ("alfombra", "NN")))==lemmatas)
   }

   /**
     * Testing Parser
     */

//   test("test parse text"){
//     val listSol1=List("Los/DT/el", "gatos/NNS/gato" ,"negros/JJ/negro", "son/VB/ser", "bonitos/NN/bonitos" ,"././.")
//     val listSol2=List("Nadie/DT/nadie","podrá/VB/poder","vivir/VB/vivir","eternamente/RB/eternamente")
//     val listSol3=List("Los/DT/el","ingenieros/NNS/ingeniero","informáticos/JJ/informático","son/VB/ser","muy/RB/muy","inteligentes/JJ/inteligente")
//     val listSol4=List("Aún/RB/Aún","no/RB/no","puedo/VB/poder","creer/VB/creer","que/IN/que","no/RB/no","asistiera/VB/asistir","a/IN/a","la/DT/el","fiesta/NN/fiesta","././.")
//     assert(myParser.parse(string1,true,true,true,tagger.parole2penntreebank)== listSol1.mkString("\n"))
//     assert(myParser.parse(string2,true,true,true,tagger.parole2penntreebank)== listSol2.mkString("\n"))
//     assert(myParser.parse(string3,true,true,true,tagger.parole2penntreebank)== listSol3.mkString("\n"))
//     assert(myParser.parse(string4,true,true,true,tagger.parole2penntreebank)== listSol4.mkString("\n"))
//   }

}