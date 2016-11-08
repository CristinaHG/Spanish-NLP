/**
  * Created by cris on 08/11/16.
  */
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AppTest extends FunSuite {

  //import class Tokenizer
  import Tokenizer

  //create trait containing Strings to be used to test
  trait TestStrings {
    val string1="Los gatos negros son bonitos."
    val string2="Nadie podrá vivir eternamente"
    val string3="Los ingenieros informáticos son muy inteligentes"
    val string4="Las atletas españolas son muy buenas deportistas"
    val string5="El cielo es azul, el agua es transparente y las amapolas son rojas."
    val string6="Los lobos aullaban desesperadamente"
    val string7="Dijo que lo esperáramos aquí mientras compraba galletas"
    val string8="Te advertimos que serían duros contigo"
    val string9="reconozco que me comporté mal"
    val string10="todo esfuerzo valdrá la pena"
    val string11="dejaron que murieran de hambre"
    val string12="nos atracaron a mano armada"
    val string13="cuando éramos niños repelíamos muchos bichos"
    val string14="con tanto calor se funde el hielo"
    val string15="lo que no queremos es que lo acaparéis todo"
    val string16="El sedentarismo físico se presenta con mayor frecuencia en la vida moderna urbana, en sociedades altamente tecnificadas en donde todo está pensado para evitar grandes esfuerzos físicos, en las clases altas y en los círculos intelectuales en donde las personas se dedican más a actividades cerebrales."
  }
  /**
    * Testing Tokenizer
    */

   test(" find tokens "){
     //instance of Strings is created
     new TestStrings {
       //if assert fails error mesage is show
       assert(find_tokens(s1).toArray.deep, Array("Los","gatos","negros","son","bonitos",".").deep , "error: tokens missmatch")
     }
   }

}