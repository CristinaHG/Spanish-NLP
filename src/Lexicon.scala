import java.nio.charset.StandardCharsets
import java.nio.file
import java.nio.file.{Files, Paths}

import com.sun.media.jfxmedia.track.Track.Encoding

import scala.collection.mutable
import scala.io.Source
import scala.reflect.io.File
import scala.xml.Comment

/**
  * Created by cris on 12/10/16.
  */

class Lexicon {

  // Returns a dictironary with readed lexicon over the lines in the file at the given path strippping comments
  def read(path: String, encoding: String, comment: String): mutable.Map[String, String] = {
    //creating empty lexicon dict
    val lexiconDict= mutable.Map[String,String]()

    if (!path.isEmpty) {
      if (path.isInstanceOf[String] && Files.exists(Paths.get(path)) ) {
        //from file path
        val f = scala.io.Source.fromFile(path).getLines().filter(line=>(!line.startsWith(comment))).map{line=>
          val x=line.split(" ")
          (x.head,x.last)}.foreach(u=>lexiconDict+=(u._1->u._2))
        //replace "\" by "" -> codecs.BOMUTF8 not necesary because of codification
       // f = f.map(line => if (f.indexOf(line) == 0 && line.isInstanceOf[String]) line.replaceAll("\"+$", "") else line)
      }else if(path.isInstanceOf[String]){
        //from String
              val f=scala.io.Source.fromString(path).getLines().filter(line=>(!line.startsWith(comment))).map{line=>
                val x=line.split(" ")
                (x.head,x.last)}.foreach(u=>lexiconDict+=(u._1->u._2))
//      }else{
//        //from file or buffer
//              val f=scala.io.Source.fromBytes(path.toBuffer.toArray,encoding).getLines().filter(line=>(!line.startsWith(comment))).map{line=>
//          val x=line.split(" ")
//          (x.head,x.last)}.foreach(u=>lexiconDict+=(u._1->u._2))
            }
      }else throw new IllegalArgumentException("a path must be specified")
    return lexiconDict
  }

}
