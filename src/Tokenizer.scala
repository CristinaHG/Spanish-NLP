import scala.util.matching.Regex
import scala.util.control.Breaks._
/**
  * Created by cris on 24/08/16.
  */


class Tokenizer {
  val punctuation = ".,;:!?()[]{}`'\"@#$^&*+-|=~_"
  val abbreviations=List("a.C.", "a.m.", "apdo.", "aprox.", "Av.", "Avda.", "c.c.", "D.", "Da.", "d.C.",
    "d.j.C.", "dna.", "Dr.", "Dra.", "esq.", "etc.", "Gob.", "h.", "m.n.", "no.",
    "núm.", "pág.", "P.D.", "P.S.", "p.ej.", "p.m.", "Profa.", "q.e.p.d.", "S.A.",
    "S.L.", "Sr.", "Sra.", "Srta.", "s.s.s.", "tel.", "Ud.", "Vd.", "Uds.", "Vds.",
    "v.", "vol.", "W.C.")

  val re_abbr1="""^[A-Za-z]\.$""".r // single letter , "D."
  val re_abbr2="""^([A-Za-z]+\.)+$""".r //alternating letters, "U.S. , apdo."
  val re_abbr3="""^[A-Z][ + "|".concat("bcdfghjklmnpqrstvwxz") + "]+.$ """.r //# capital followed by consonants, "Mr."

  // Handle paragraph line breaks (\n\n marks end of sentence).
  val EOS = "END-OF-SENTENCE"
  var TOKEN="""(\S+)\s""".r
  //return a list of sentences. Each sentence is a space-separated string of tokens.
  //handles common abreviations.Punctuation marks are split fron other words. Periods or ?! mark the end of a sentence.
  //Headings without ending period are inferred by line breaks.
  def find_tokens(string:String):List[String]={
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
    var tokens=List()

    //handle punctuation marks
    TOKEN.findAllIn(s).foreach(t => if(t.length>0){
                        var tail=Nil
                        var t2=t
                        while (punc.contains(t2.head)){
                         t2.head:: tokens
                          tokens=tokens.reverse
                          t2=t2.tail
                        }
                        while (t2.endsWith(punc) || t2.endsWith(".")){
                          if(t2.endsWith(punc)){
                            tail.+(t2.substring(0,t2.length-1))
                            t2=t2.tail
                        }//split elipsis (...) before splitting period
                          if(t2.endsWith("...")){
                            tail.+("...")
                            t2=t2.substring(0,t2.length-3)
                          }
                          //split period(if not an abbreviation)
                          if(t2.endsWith(".")){
                            if(abbreviations.contains(t2) || re_abbr1.findAllMatchIn(t2).length>0 || re_abbr2.findAllMatchIn(t2).length>0 ||
                              re_abbr3.findAllMatchIn(t2).length>0) break()
                          }else{
                            tail.+(t2.substring(t2.length))
                            t2=t2.tail
                          }
                        }
                        if( t2.compareTo("")!=0){
                          tokens.+(t2)
                        }
                        tokens.++(tail.reverse)
    })
    //handle sentence breaks (periods,quotes,parenthesis)
    var j=0
    var i=0
    var sentences:List[String]=Nil
    breakable{ while (j < tokens.length){

      if(tokens(j)=="..." || tokens(j)=="." || tokens(j)=="!" || tokens(j)=="?" || tokens(j)==EOS){
          while(j < tokens.length && (tokens(j)=="'" || tokens(j)=="\"" || tokens(j)=="”" || tokens(j)=="’" || tokens(j)=="..."
            || tokens(j)== "." || tokens(j)=="!" || tokens(j)=="?" || tokens(j)==")" || tokens(j)==EOS)  ){
            if( (tokens(j)=="'" || tokens(j)=="\"") && sentences(sentences.length-1).count(tokens(j))%2==0 ) break()
            j+=1
            sentences(sentences.length-1).++( tokens.slice(i,j).filter(t=>t!=EOS))
            sentences.++(List())
            i=j
          }
        j+=1
      }
    }}
    //handle emoticons
    return sentences
  }
}
