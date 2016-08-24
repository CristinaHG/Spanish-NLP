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

  val re_abbr1="""^[A-Za-z]\.$""".r // single letter , "T. De Smedt"
  val re_abbr2="""^([A-Za-z]\.)+$""" //alternating letters, "U.S"
  val re_abbr3="""^[A-Z][" + "|".join("bcdfghjklmnpqrstvwxz") + "]+.$ """ //# capital followed by consonants, "Mr."
}
