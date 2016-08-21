/**
  * Created by cris on 15/08/16.
  */
import scala.io.Source

class Lemmatizer {


  var lista = List(("Los", "DT"), ("gatos", "NNS"),("negros", "JJ"),("son", "VB"),("horribles", "JJ"))
  val verbsDict="/home/cris/mrcrstnherediagmez@gmail.com/Spanish_Lematizer/src/es-verbs.txt"
  val irregular_inflections=List(
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
  //-----------load file and make a dictionary pair[verb form, infinitive] method

  def verbsToDictionaryPair(file: String):Map[String,String]={
    var dict:Map[String,String]=Map()
    for (line<-Source.fromFile(file).getLines().filterNot(_.startsWith(";"))){ //not filter comments
      val a=line.split(",")
      //dict+=("comíamos"->"comer")
      a.foreach(u=>dict+=(u->a{0}))
    }
    return dict
  }

  //-----returns the infinitive form of the given verb or none
  def lemma(verb:String,dictionary:Map[String,String]):String={
  if(dictionary.getOrElse(verb," ")!="") return dictionary(verb).toLowerCase
  else return find_lemma(verb)
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
  //saldrár => saler
if(v.contains("ldr")) return v.substring(0,v.indexOf("ldr")+1).concat("er")
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
  regular_inflection_er.foreach(u=>if(v.endsWith(u)){if(v.substring(0,v.length-u.length).length>2 && v.charAt(v.length-2)=="i")
  return v.substring(0,v.length-u.length).concat("ir") else return v.substring(0,v.length-u.length).concat("er")} )

  //Many verbs end in -ir and have a regular inflection
  val regular_inflection_ir=List(
    "iré", "irás", "irá", "iremos", "iréis", "irán", //future
  "iría", "irías", "iríamos", "iríais", "irían")  //past subjunctive

  regular_inflection_ir.foreach(u=>if(v.endsWith(u)) return v.substring(0,v.length-u.length).concat("ir"))
  //Present 1sg -o: yo hablo, como, vivo => hablar, comer, vivir.
  if(v.endsWith("o")) return v.substring(0,v.length-1).concat("ar")
  //Present 2sg, 3sg and 3pl: tú hablas.
  if(v.endsWith("a")) return v.substring(0,v.length-2).concat("ar")
  if(v.endsWith("as") || v.endsWith("an")) return v.substring(0,v.length-1).concat("ar")
  // Present 2sg, 3sg and 3pl: tú comes, tú vives.
  if(v.endsWith("e")){
    if(v.substring(0,v.length-1).length>2 && v.charAt(v.length-2)=="i") return v.substring(0,v.length-1).concat("ir")
    else return v.substring(0,v.length-1).concat("er")
  }
  
}


  //----------singularize method
  def singularize(word: String, pos:String ):String={
    val w=word.toLowerCase()
    //los gatos=> el gato
    if (pos=="DT"){
      if(w.compareTo("la")==0 || w.compareTo("las")==0 || w.compareTo("los")==0) return "el"
      if(w.compareTo("una")==0 || w.compareTo("unas")==0 || w.compareTo("unos")==0) return "un"
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
  def isVowel(char: Char):Boolean={
   if (char.equals("a","e","i","o","u"))
    return true
    else return false
  }

  //---------normalize
  def normalize(char: Char):Char={
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
  if (w.endsWith("os") || w.endsWith("as")) return w.substring(0,w.length-1)
    // histérico=>histérico
    if (w.endsWith("o")) return w
    //histérica=>histérico
    if(w.endsWith("a")) return w.substring(0,w.length-1).concat("o")
    //horribles=>horrible, humorales=>humoral
    if(w.endsWith("es")) {
      if (w.length >= 4 && !(isVowel(normalize(w.charAt(w.length - 3)))) && !isVowel(normalize(w.charAt(w.length - 4)))) return w.substring(0, w.length - 1)
    }else return w.substring(0,w.length-2)
    return w
  }

  //----------conjugate verb to infinitive form
//  def toInfinitive(verb:String):String={
//
//  }

  def get_lemmas(l: List[(String,String)]): List[(String, String,String)] = {
    var word :String=""
    var lemma:String=""
    var pos:String=""
    var lemmalist: List[(String, String, String)] = List()

  l.foreach( i => {word=i._1;pos=i._2;lemma=i._1;
  if (pos.startsWith("DT")) lemma=singularize(word,"DT")
  if (pos.startsWith("JJ")) lemma=predicative(word)
  if (pos.startsWith("NNS")) lemma=singularize(word,"NNS")
    if (pos.startsWith("VB") || pos.startsWith("MD")) //lemma= toInfinitive(word)
    lemmalist= (word,pos,lemma)::lemmalist
 })
    return lemmalist.reverse
  }







}

object ScalaApp {
  def  main(args: Array[String]) {
 val lemmatizr=new Lemmatizer
//    print(lemmatizr.lista(1)._1)
//    val lemas=lemmatizr.get_lemmas(lemmatizr.lista)
//    lemas.foreach(i=>print(i))
    val mappedVerbs=lemmatizr.verbsToDictionaryPair(lemmatizr.verbsDict)
    print(mappedVerbs)
  }
}



