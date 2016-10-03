import java.util.regex.Pattern

import scala.collection.mutable
import scala.util.matching.Regex
import scala.util.control.Breaks._
/**
  * Created by cris on 24/08/16.
  */


 class Tokenizer{
  val punctuation = ".,;:!?()[]{}`'\"@#$^&*+-|=~_"
  val abbreviations=List("a.C.", "a.m.", "apdo.", "aprox.", "Av.", "Avda.", "c.c.", "D.", "Da.", "d.C.",
    "d.j.C.", "dna.", "Dr.", "Dra.", "esq.", "etc.", "Gob.", "h.", "m.n.", "no.",
    "núm.", "pág.", "P.D.", "P.S.", "p.ej.", "p.m.", "Profa.", "q.e.p.d.", "S.A.",
    "S.L.", "Sr.", "Sra.", "Srta.", "s.s.s.", "tel.", "Ud.", "Vd.", "Uds.", "Vds.",
    "v.", "vol.", "W.C.")

  val re_abbr1="""^[A-Za-z]\.$""".r // single letter , "D."
  val re_abbr2="""^([A-Za-z]\.)+$""".r //alternating letters, "U.S. , apdo."
  val re_abbr3="""^[A-Z][ + "|".concat("bcdfghjklmnpqrstvwxz") + "]+.$ """.r //# capital followed by consonants, "Mr."
  val re_sarcasm="""\( ?\! ?\)""".r //handle sarcasm punctuation (!)
  val emoticons= mutable.Map[(String,Double),List[String]]()
  emoticons+=(("love" , 1.00) ->List("<3","♥"))
  emoticons+=(("grin" , 1.00)->List(">:D", ":-D", ":D", "=-D", "=D", "X-D", "x-D", "XD", "xD", "8-D"))
  emoticons+=(("taunt", +0.75)->List(">:P", ":-P", ":P", ":-p", ":p", ":-b", ":b", ":c)", ":o)", ":^)"))
  emoticons+=(("smile", +0.50)->List(">:)", ":-)", ":)", "=)", "=]", ":]", ":}", ":>", ":3", "8)", "8-)"))
  emoticons+=(("wink" , +0.25)->List(">;]", ";-)", ";)", ";-]", ";]", ";D", ";^)", "*-)", "*)"))
  emoticons+=(("gasp" , +0.05)->List(">:o", ":-O", ":O", ":o", ":-o", "o_O", "o.O", "°O°", "°o°"))
  emoticons+=(("worry", -0.25)->List(">:/",  ":-/", ":/", ":\\", ">:\\", ":-.", ":-s", ":s", ":S", ":-S", ">.>"))
  emoticons+=(("frown", -0.75)->List(">:[", ":-(", ":(", "=(", ":-[", ":[", ":{", ":-<", ":c", ":-c", "=/"))
  emoticons+=(("cry"  , -1.00)->List(":'(", ":'''(", ";'("))

var re_emoticons=""::Nil
  emoticons.values.foreach(list=>re_emoticons:::=list.flatMap(elem=> if(elem.compareTo(list.last)!=0) "|"::elem::Nil else "|"::elem::"|"::Nil).tail)
  var re1_emoticons=""::Nil
  re_emoticons.foreach(icon=>if(!(icon.equals("|"))) re1_emoticons::=icon.mkString("?".concat("""\""")) else re1_emoticons::=icon )

  re1_emoticons=re1_emoticons.map(t=>
      if( t.contains("D")) t.dropRight(t.length-t.indexOf("D")+1)+t.last
      else if (t.contains("S")) t.dropRight(t.length-t.indexOf("S")+1)+t.last
      else if (t.contains("s")) t.dropRight(t.length-t.indexOf("s")+1)+t.last
      else if (t.contains("b")) t.dropRight(t.length-t.indexOf("b")+1)+t.last
      else if (t.contains("c")) t.dropRight(t.length-t.indexOf("c")+1)+t.last
      else t
  )

  re1_emoticons=re1_emoticons.reverse.tail.map(t => if(!(t.equals("|"))) """\"""+t else t )  //important to reserve and to eliminate the first

  var RE_EMOTICONS=Pattern.quote(re1_emoticons.dropRight(2).mkString).r
  print(RE_EMOTICONS)
  val EOS = "END-OF-SENTENCE"
  var TOKEN="""(\S+)\s""".r
  //return a list of sentences. Each sentence is a space-separated string of tokens.
  //handles common abreviations.Punctuation marks are split fron other words. Periods or ?! mark the end of a sentence.
  //Headings without ending period are inferred by line breaks.
  def find_tokens(string:String):mutable.MutableList[String]={
    var s=string
    val punc=punctuation.replace(".","").toCharArray
//    var lista=Nil
//    punc.foreach(p=>(p)::lista)
//    //lista=lista.reverse
  //handle unicode quotes
    if(s.contains("“")) s.replace("“"," “ ")
    if(s.contains("”")) s.replace("”"," ” ")
    if(s.contains("‘")) s.replace("‘"," ‘ ")
    if(s.contains("’")) s.replace("’"," ’ ")
   //collapse whitespace
    s="\r\n".r.replaceAllIn(s,"\n")
    s="\n{2,}".r.replaceAllIn(s,EOS)
    s="""\s+""".r.replaceAllIn(s," ")

    //find words
    var tokens=mutable.MutableList[String]()

    //handle punctuation marks
    TOKEN.findAllIn(s+" ").foreach(t => if(t.length>0){
                        var tail=mutable.MutableList[String]()
                        var t2=t.stripSuffix(" ")
                        while (punc.contains(t2.head)){
                         tokens+=t2.head.toString
                         //tokens=tokens.reverse
                          t2=t2.tail
                        }
                        while (punc.contains(t2.last) || t2.endsWith(".")) {
                          if (t2.endsWith("...")) {
                            tail+=("...")
                            t2 = t2.substring(0, t2.length - 3)
                          }
                          else if (punc.contains(t2.last)) {
                            tail+=(t2.substring(t2.length - 1))
                            t2 = t2.substring(0,t2.length-1)
                          } //split elipsis (...) before splitting period

                          //split period(if not an abbreviation)
                          if (t2.endsWith(".")) {
                            if ((abbreviations.contains(t2) || re_abbr1.findAllMatchIn(t2).length > 0 || re_abbr2.findAllMatchIn(t2).length > 0 ||
                              re_abbr3.findAllMatchIn(t2).length > 0)!=true) {

                              tail+=(t2.substring(t2.length-1))
                              t2 = t2.substring(0,t2.length-1)
                            }
                          }
                        }
                        if( t2.compareTo("")!=0){
                          tokens+=t2
                        }
                        if(!tail.isEmpty) {
                          tail.foreach(u=> tokens+=u.toString )
                        }
    })
    //handle sentence breaks (periods,quotes,parenthesis)
    var j=0
    var i=0
    var sentences=mutable.MutableList[String]()

    breakable{ while (j < tokens.length){

      if(tokens(j)=="..." || tokens(j)=="." || tokens(j)=="!" || tokens(j)=="?" || tokens(j)==EOS){
          while(j < tokens.length && (tokens(j)=="'" || tokens(j)=="\"" || tokens(j)=="”" || tokens(j)=="’" || tokens(j)=="..."
            || tokens(j)== "." || tokens(j)=="!" || tokens(j)=="?" || tokens(j)==")" || tokens(j)==EOS)  ) {
            if ((tokens(j) == "'" || tokens(j) == "\"") && (sentences.last.count(_ == tokens(j)) % 2 == 0)) {
              break()
            }
            j += 1
          }

            sentences+=tokens.slice(i,j).filter(t=>t!=EOS).mkString(" ")
            //tokens.slice(i,j).filter(t=>t!=EOS).forall(t=>sentences+=t)
            sentences+=" "
            i=j
      }
      j+=1
    }
      //tokens.slice(i,j).filter(t=>t!=EOS).foreach(t=>sentences+=t)
      sentences+=tokens.slice(i,j).filter(t=>t!=EOS).mkString(" ")
      //handle emoticons


    }
    return sentences
  }

  def get_sentences(sentences:List[String]): Unit ={
      sentences.foreach(p=>print(p+"\n"))
  }

  def count_sentences(sentences:List[String]): Unit ={
    return sentences.length
  }


}
