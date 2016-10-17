import java.nio.charset.StandardCharsets
import java.nio.file
import java.nio.file.{Files, Paths}

import com.sun.media.jfxmedia.track.Track.Encoding

import scala.collection.mutable
import scala.io.Source
import scala.xml.Comment

/**
  * Created by cris on 12/10/16.
  */
// Returns an iterator over the lines in the file at the given path,
//        strippping comments and decoding each line to Unicode.

class Lexicon {


  def read(path: String, encoding: String, comment: String): mutable.Map[String, String] = {
    //creating empty lexicon list
    val emoticons= mutable.Map[String,String]()
    if (!path.isEmpty) {
      if (path.isInstanceOf[String] && Files.exists(Paths.get(path)) ) {
        //from file path
        var f = scala.io.Source.fromFile(path).getLines().filter(line=>(!line.startsWith(comment))).map{line=>
          val x=line.split(" ")
          (x.head,x.last)}.foreach(u=>emoticons+=(u._1->u._2))
        //.foreach(s=> emoticons+=(s(0)->s(1)))
       // f = f.map(line => if (f.indexOf(line) == 0 && line.isInstanceOf[String]) line.replaceAll("\"+$", "") else line)
        //f.foreach(line=> line.split(" ").foreach(s=> emoticons+=(s(0)->s(1))))
       // f = f.filterNot(line => line.startsWith(comment))

        //    }else if(path.isInstanceOf[String]){
        //      //from String
        //      val f=scala.io.Source.fromString(path)
        //    }else{
        //      //from file or buffer
        //      val f=path
        //    }
      }
    } //else throw new IllegalArgumentException("a path must be specified")
    return emoticons
  }

}
