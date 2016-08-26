import scala.util.matching.Regex

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

  //return a list of sentences. Each sentence is a space-separated string of tokens.
  //handles common abreviations.Punctuation marks are split fron other words. Periods or ?! mark the end of a sentence.
  //Headings without ending period are inferred by line breaks.
  def find_tokens(string:String):String={
    var s=string
    val punc=punctuation.replace(".","")
    var lista:List[Char]=Nil
    punc.foreach(p=>(p)::lista)
    lista=lista.reverse
  //handle unicode quotes
    if(s.contains("“")) s.replace("“"," “ ")
    if(s.contains("”")) s.replace("”"," ” ")
    if(s.contains("‘")) s.replace("‘"," ‘ ")
    if(s.contains("’")) s.replace("’"," ’ ")
   //collapse whitespace
    s="\r\n".r.replaceAllIn(s,"\n")
    s="\n{2,}".r.replaceAllIn(s,EOS)
    s="""\s+""".r.replaceAllIn(s," ")

  }
}
