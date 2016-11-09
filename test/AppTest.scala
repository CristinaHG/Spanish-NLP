/**
  * Created by cris on 08/11/16.
  */
import org.scalatest._
import org.junit.runner.RunWith
//import org.scalatest.junit.JUnitRunner
import org.junit.Test
import org.junit.Assert.assertArrayEquals


 class TokenizerTest extends FunSuite {


  //create trait containing Strings to be used to test

    val string1="Los gatos negros son bonitos."
    val string2="Nadie podrá vivir eternamente"
    val string3="Los ingenieros informáticos son muy inteligentes"
    val string4="Aún no puedo creer que Trump haya ganado las elecciones."
    val string5="El cielo es azul. El agua es transparente y las amapolas son rojas."
    val string6="Los lobos aullaban desesperadamente"
    val string7="Dijo que lo esperáramos aquí mientras compraba galletas"
    val string8="Te advertimos que serían duros contigo"
    val string9="reconozco que me comporté mal"
    val string10="todo esfuerzo valdrá la pena"
    val string11="dejaron que murieran de hambre"
    val string12="nos atracaron a mano armada"
    val string13="cuando éramos niños cazábamos muchos bichos"
    val string14="con tanto calor se funde el hielo"
    val string15="El sedentarismo físico se presenta con mayor frecuencia en la vida moderna urbana, en sociedades altamente tecnificadas en donde todo está pensado para evitar grandes esfuerzos físicos, en las clases altas y en los círculos intelectuales en donde las personas se dedican más a actividades cerebrales."
    val tokenizer=new Tokenizer

  /**
    * Testing Tokenizer
    */
test("find tokens test"){
   tokenizer.find_tokens(string1)
  assert(tokenizer.find_tokens(string1)==List(List("Los","gatos","negros","son","bonitos",".")))
  assert(tokenizer.find_tokens(string2)==List(List("Nadie","podrá","vivir","eternamente")))
  assert(tokenizer.find_tokens(string3)==List(List("Los","ingenieros","informáticos","son","muy","inteligentes")))
  assert(tokenizer.find_tokens(string4)==List(List("Aún","no","puedo","creer","que","Trump","haya","ganado","las","elecciones",".")))
  assert(tokenizer.find_tokens(string5)==List(List("El","cielo","es","azul"),List("El","agua","es","transparente","y","las","amapolas","son","rojas",".")))
   assert(tokenizer.find_tokens(string6)==List(List("Los","lobos","aullaban","desesperadamente")))
   assert(tokenizer.find_tokens(string7)==List(List("Dijo","que","lo","esperáramos","aquí","mientras","compraba","galletas")))
   assert(tokenizer.find_tokens(string8)==List(List("Te","advertimos","que","serían","duros","contigo")))
   assert(tokenizer.find_tokens(string9)==List(List("reconozco","que","me","comporté","mal")))
   assert(tokenizer.find_tokens(string10)==List(List("todo","esfuerzo","valdrá","la","pena")))
   assert(tokenizer.find_tokens(string11)==List(List("dejaron","que","murieran","de","hambre")))
   assert(tokenizer.find_tokens(string12)==List(List("nos","atracaron","a","mano","armada")))
   assert(tokenizer.find_tokens(string13)==List(List("cuando","éramos","niños","cazábamos","muchos","bichos")))
   assert(tokenizer.find_tokens(string14)==List(List("con","tanto","calor","se","funde","el","hielo")))
   assert(tokenizer.find_tokens(string15)==List(List("El","sedentarismo","físico","se","presenta","con","mayor","frecuencia","en","la","vida","moderna","urbana",",","en","sociedades","altamente","tecnificadas","en","donde","todo","está","pensado","para","evitar","grandes","esfuerzos","físicos",",","en","las","clases","altas","y","en","los","círculos","intelectuales","en","donde","las","personas","se","dedican","más","a","actividades","cerebrales","."
/   )))





}

}