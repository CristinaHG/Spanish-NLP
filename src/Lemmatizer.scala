/**
  * Created by cris on 15/08/16.
  */
import scala.io.Source

class Lemmatizer {

  //verbs dictionary
  private[this] var mappedVerbs = Map[String, String]()
  private[this] val irregular_inflections=List(
    ("yéramos", "ir"   ), ( "cisteis", "cer"   ), ( "tuviera", "tener"), ( "ndieron", "nder" ),
    ( "ndiendo", "nder" ), ("tándose", "tarse" ), ( "ndieran", "nder" ), ( "ndieras", "nder" ),
    ("izaréis", "izar" ), ( "disteis", "der"   ), ( "irtiera", "ertir"), ( "pusiera", "poner"),
    ( "endiste", "ender"), ( "laremos", "lar"   ), ("ndíamos", "nder" ), ("icaréis", "icar" ),
    ("dábamos", "dar"  ), ( "intiera", "entir" ), ( "iquemos", "icar" ), ("jéramos", "cir"  ),
    ( "dierais", "der"  ), ( "endiera", "ender" ), ("iéndose", "erse" ), ( "jisteis", "cir"  ),
    ( "cierais", "cer"  ), ("ecíamos", "ecer"  ), ( "áramos", "ar"   ), ( "ríamos", "r"    ),
    ( "éramos", "r"    ), ( "iríais", "ir"    ), (   "temos", "tar"  ), (   "steis", "r"    ),
    (   "ciera", "cer"  ), (   "erais", "r"     ), (   "timos", "tir"  ), (   "uemos", "ar"   ),
    (   "tiera", "tir"  ), (   "bimos", "bir"   ), (  "ciéis", "ciar" ), (   "gimos", "gir"  ),
    (   "jiste", "cir"  ), (   "mimos", "mir"   ), (  "guéis", "gar"  ), (  "stéis", "star" ),
    (   "jimos", "cir"  ), (  "inéis", "inar"  ), (   "jemos", "jar"  ), (   "tenga", "tener"),
    (  "quéis", "car"  ), (  "bíais", "bir"   ), (   "jeron", "cir"  ), (  "uíais", "uir"  ),
    (  "ntéis", "ntar" ), (   "jeras", "cir"   ), (   "jeran", "cir"  ), (  "ducía", "ducir"),
    (   "yendo", "ir"   ), (   "eemos", "ear"   ), (   "ierta", "ertir"), (   "ierte", "ertir"),
    (   "nemos", "nar"  ), (  "ngáis", "ner"   ), (   "liera", "ler"  ), (  "endió", "ender"),
    (  "uyáis", "uir"  ), (   "memos", "mar"   ), (   "ciste", "cer"  ), (   "ujera", "ucir" ),
    (   "uimos", "uir"  ), (   "ienda", "ender" ), (  "lléis", "llar" ), (   "iemos", "iar"  ),
    (   "iende", "ender"), (   "rimos", "rir"   ), (   "semos", "sar"  ), (  "itéis", "itar" ),
    (  "gíais", "gir"  ), (  "ndáis", "nder"  ), (  "tíais", "tir"  ), (   "demos", "dar"  ),
    (   "lemos", "lar"  ), (   "ponga", "poner" ), (   "yamos", "ir"   ), (  "icéis", "izar" ),
    (    "bais", "r"    ), (   "rías", "r"     ), (   "rían", "r"    ), (   "iría", "ir"   ),
    (    "eran", "r"    ), (    "eras", "r"     ), (   "irán", "ir"   ), (   "irás", "ir"   ),
    (    "ongo", "oner" ), (    "aiga", "aer"   ), (   "ímos", "ir"   ), (   "ibía", "ibir" ),
    (    "diga", "decir"), (   "edía", "edir"  ), (    "orte", "ortar"), (   "guió", "guir" ),
    (    "iega", "egar" ), (    "oren", "orar"  ), (    "ores", "orar" ), (   "léis", "lar"  ),
    (    "irme", "irmar"), (    "siga", "seguir"), (   "séis", "sar"  ), (   "stré", "strar"),
    (    "cien", "ciar" ), (    "cies", "ciar"  ), (    "dujo", "ducir"), (    "eses", "esar" ),
    (    "esen", "esar" ), (    "coja", "coger" ), (    "lice", "lizar"), (   "tías", "tir"  ),
    (   "tían", "tir"  ), (    "pare", "parar" ), (    "gres", "grar" ), (    "gren", "grar" ),
    (    "tuvo", "tener"), (   "uían", "uir"   ), (   "uías", "uir"  ), (    "quen", "car"  ),
    (    "ques", "car"  ), (   "téis", "tar"   ), (    "iero", "erir" ), (    "iere", "erir" ),
    (    "uche", "uchar"), (    "tuve", "tener" ), (    "inen", "inar" ), (    "pire", "pirar"),
    (   "reía", "reir" ), (    "uste", "ustar" ), (   "ibió", "ibir" ), (    "duce", "ducir"),
    (    "icen", "izar" ), (    "ices", "izar"  ), (    "ines", "inar" ), (    "ires", "irar" ),
    (    "iren", "irar" ), (    "duje", "ducir" ), (    "ille", "illar"), (    "urre", "urrir"),
    (    "tido", "tir"  ), (   "ndió", "nder"  ), (    "uido", "uir"  ), (    "uces", "ucir" ),
    (    "ucen", "ucir" ), (   "iéis", "iar"   ), (   "eció", "ecer" ), (   "jéis", "jar"  ),
    (    "erve", "ervar"), (    "uyas", "uir"   ), (    "uyan", "uir"  ), (    "tía", "tir"  ),
    (    "uía", "uir"  ), (     "aos", "arse"  ), (     "gue", "gar"  ), (    "qué", "car"  ),
    (     "que", "car"  ), (     "rse", "rse"   ), (     "ste", "r"    ), (     "era", "r"    ),
    (    "tió", "tir"  ), (     "ine", "inar"  ), (     "ré", "r"    ), (      "ya", "ir"   ),
    (      "ye", "ir"   ), (     "tí", "tir"   ), (     "cé", "zar"  ), (      "ie", "iar"  ),
    (      "id", "ir"   ), (     "ué", "ar"    )
    )

  //get verbsdDictionary
   def getVerbsDict:Map[String,String]={
    return mappedVerbs
  }

  //-----------load file and make a dictionary pair[verb form, infinitive] method

  private def verbsToDictionaryPair(file: String):Map[String,String]={
    var dict=Map[String,String]()
    for (line<-Source.fromFile(file).getLines().filterNot(_.startsWith(";"))){ //not filter comments
      val a=line.split(",")
      //dict+=("comíamos"->"comer")
      a.filter(p=>p!="").foreach(u=>if(!dict.contains(u)) dict+=(u->a(0)))
    }
    return dict
  }

  //---------changes intern verbs dictionary to a given one or a given path
  def setVerbsDict(dictionaryPath:String)={
    mappedVerbs=verbsToDictionaryPair(dictionaryPath)
  }

  //-----returns the infinitive form of the given verb or none
  private def verb_lemma(verb:String):String={
  //if(dictionary.getOrElse(verb," ")!="") return dictionary(verb).toLowerCase
  val v=verb.toLowerCase
    if( mappedVerbs .keySet.exists(_==v)){
      return this.mappedVerbs(v)
    }else return find_lemma(v)
  }

  //-------return the base form of verb using a rule-based aproach when verb is unknow
def find_lemma(verb:String):String={
  // Spanish has 12,000+ verbs, ending in -ar (85%), -er (8%), -ir (7%).
  // Over 65% of -ar verbs (6500+) have a regular inflection.
  var v = verb.toLowerCase
  if(verb.endsWith("ar") || verb.endsWith("er") || verb.endsWith("ir")) return verb //verb is infinitive
  //set of rules for irregular inflections +10%

  irregular_inflections.foreach(a=> if(v.endsWith(a._1)) return v.substring(0,v.length-a._1.length).concat(a._2))
  //reconozco=>reconocer
  v=v.replace("zco","ce")
  //reconozcamos=>reconocer
  v=v.replace("zca","ce")
  //reconozcáis=>reconocer
  v=v.replace("zcá","ce")
  //valdrá => valer
if(v.contains("ldr")) return v.substring(0,v.indexOf("ldr")+1).concat("er")
  //contendrá=>contener
  if(v.contains("ndr")) return v.substring(0,v.indexOf("ndr")+1).concat("er")
//many verbs end in -ar and have a regular inflection
val regular_inflection_ar=List (
  "ando", "ado", "ad",                                // participle
  "aré", "arás", "ará", "aremos", "aréis", "arán", // future
  "aría", "arías", "aríamos", "aríais", "arían",    // conditional
  "aba", "abas", "ábamos", "abais", "aban",         // past imperfective
    "é", "aste", "ó", "asteis", "aron",               // past perfective
    "ara", "aras", "áramos", "arais", "aran")       // past subjunctive

regular_inflection_ar.foreach(u=> if (v.endsWith(u)) return v.substring(0,v.length-u.length).concat("ar"))
//Many verbs end in -er and have a regular inflection
  val regular_inflection_er=List(
      "iendo", "ido", "ed",                               // participle
  "eré", "erás", "erá", "eremos", "eréis", "erán", // future
  "ería", "erías", "eríamos", "eríais", "erían",    // conditional
  "ía", "ías", "íamos", "íais", "ían",              // past imperfective
    "í", "iste", "ió", "imos", "isteis", "ieron",        // past perfective
    "era", "eras", "éramos", "erais", "eran")       //past subjunctive

  regular_inflection_er.foreach(u=>if(v.endsWith(u)){
    val difLength=v.length-u.length
    if(v.substring(0,difLength).length>2 && v.substring(0,difLength).charAt(difLength-2)=='i')
  return v.substring(0,difLength).concat("ir") else return v.substring(0,difLength).concat("er")} )

  //Many verbs end in -ir and have a regular inflection
  val regular_inflection_ir=List(
    "iré", "irás", "irá", "iremos", "iréis", "irán", //future
  "iría", "irías", "iríamos", "iríais", "irían")  //past subjunctive

  regular_inflection_ir.foreach(u=>if(v.endsWith(u)) return v.substring(0,v.length-u.length).concat("ir"))
  //Present 1sg -o: yo hablo, como, vivo => hablar, comer, vivir.
  if(v.endsWith("o")) return v.substring(0,v.length-1).concat("ar")
  //Present 2sg, 3sg and 3pl: tú hablas.
  if(v.endsWith("a")) return v.substring(0,v.length-1).concat("ar")

  if(v.endsWith("as") || v.endsWith("an")){
    if (v.endsWith("as")) v =v.stripSuffix("s").dropRight(1)
    else if( v.endsWith("an")) v=v.stripSuffix("n").dropRight(1)
   return v.concat("ar")
  }
  // Present 2sg, 3sg and 3pl: tú comes, tú vives.
  if(v.endsWith("e")){
    if(v.substring(0,v.length-1).length>2 && v.substring(0,v.length-1).charAt(v.length-3)=='i') return v.substring(0,v.length-1).concat("ir")
    else return v.substring(0,v.length-1).concat("er")
  }
  if(v.endsWith("es") || v.endsWith("en")){
    if (v.endsWith("es")) v =v.stripSuffix("s").dropRight(1)
    else if( v.endsWith("en")) v=v.stripSuffix("n").dropRight(1)
    if(v.length>2 && v.charAt(v.length-2)=='i') return v.concat("ir")
  else return v.concat("er")
  }
  // Present 1pl and 2pl: nosotros hablamos.

  val present_plural_inflection_o=List(
    ("amos", "áis"),
    ("emos", "éis"),
    ("imos", "ís")
  )
  val terminations=Array("ar","er","ir")
  //terminations{1}
  present_plural_inflection_o.foreach(u=> {
    if (v.endsWith(u._1)) return v.substring(0, v.length - u._1.length).concat(terminations {
      present_plural_inflection_o.indexOf(u)
    })

    if (v.endsWith(u._2)) return v.substring(0, v.length - u._2.length).concat(terminations {
      present_plural_inflection_o.indexOf(u)
    })

  })

  return v
}


  //----------singularize method
  def singularize(word: String, pos:String ):String={
    val w=word.toLowerCase()
    //los gatos=> el gato
    if (pos=="DT"){
      if(List("la","las","los").contains(w)) return "el"
      if(List("una","unas","unos").contains(w)) return "un"
    }
    //hombres=>hombre
    if (w.endsWith("es") && (w.substring(0,w.length-2).endsWith("br") || (w.substring(0,w.length-2).endsWith("i")) ||
      (w.substring(0,w.length-2).endsWith("j")) || (w.substring(0,w.length-2).endsWith("t")) || (w.substring(0,w.length-2).endsWith("zn"))))
      return w.substring(0,w.length-1)
    //gestiones=>gestión
    val endings=List(("anes", "án"),
      ("enes", "én"),
      ("eses", "és"),
      ("ines", "ín"),
      ("ones", "ón"),
      ("unes", "ún"))
    endings.foreach(e=> {if(w.endsWith(e._1)) return w.substring(0,w.length-4).concat(e._2)})
    //hipotesis=>hipotesis
    if (w.endsWith("esis") || w.endsWith("osis") || w.endsWith("isis")) return w
    //luces=>luz
    if(w.endsWith("ces")) return w.substring(0,w.length-3).concat("z")
    //hospitales=>hospital
    if (w.endsWith("es")) return w.substring(0,w.length-2)
    //gatos=>gato
    if (w.endsWith("s")) return w.substring(0,w.length-1)

   else return w
  }

  //--------isVowel
  private def isVowel(char: Char):Boolean={
   if (List('a','e','i','o','u').contains(char))
    return true
    else return false
  }

  //---------normalize
  private def normalize(char: Char):Char={
    char match {
      case 'á' => 'a'
      case 'é' => 'e'
      case 'í'=> 'i'
      case 'ó' => 'o'
      case 'ú' => 'u'
      case _ => char
    }
  }

  //---------------predicative method

  def predicative(word:String):String={
  var w=word.toLowerCase()
    //histéricos=>histérico
  if (w.endsWith("os") || w.endsWith("as")) w=w.substring(0,w.length-1)
    // histérico=>histérico
    if (w.endsWith("o")) w= w
    //histérica=>histérico
    if(w.endsWith("a")) w= w.substring(0,w.length-1).concat("o")
    //horribles=>horrible, humorales=>humoral
    if(w.endsWith("es")){
      if (w.length >= 4 && !(isVowel(normalize(w.charAt(w.length - 3)))) && !(isVowel(normalize(w.charAt(w.length - 4))))) w= w.substring(0, w.length - 1)
      else w= w.substring(0,w.length-2)
    }
    return w
  }


  def get_lemmas(l: List[(String,String)]): List[(String, String,String)] = {
    var word :String=""
    var lemma:String=""
    var pos:String=""
    var lemmalist: List[(String, String, String)] = List()

  l.foreach( i => {word=i._1;pos=i._2;lemma=i._1;
  if (pos.startsWith("DT")) lemma=singularize(word,"DT")
  if (pos.startsWith("JJ")) lemma=predicative(word)
  if (pos.startsWith("NNS")) lemma=singularize(word,"NNS")
    if (pos.startsWith("VB") || pos.startsWith("MD")) lemma= verb_lemma(word)
    lemmalist= (word,pos,lemma)::lemmalist
 })
    return lemmalist.reverse
  }


}





