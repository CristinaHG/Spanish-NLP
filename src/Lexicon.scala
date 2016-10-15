import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import com.sun.media.jfxmedia.track.Track.Encoding

import scala.io.Source
import scala.xml.Comment

/**
  * Created by cris on 12/10/16.
  */
// Returns an iterator over the lines in the file at the given path,
//        strippping comments and decoding each line to Unicode.

def read(path:String,encoding:String,comment:String): Unit = {
  // var f:Any=""
  if (!path.isEmpty) {
    if (path.isInstanceOf[String] && Files.exists(Paths.get(path))) {
      //from file path
      var f = scala.io.Source.fromFile(path).getLines()
      f = f.map(line => if (f.indexOf(line) == 0 && line.isInstanceOf[String]) line.replaceAll("\"+$", "").getBytes(StandardCharsets.UTF_8).mkString else line.getBytes(StandardCharsets.UTF_8).mkString)
      f = f.filterNot(line => line.startsWith(comment))
      return f
      //    }else if(path.isInstanceOf[String]){
      //      //from String
      //      val f=scala.io.Source.fromString(path)
      //    }else{
      //      //from file or buffer
      //      val f=path
      //    }
    }
  } else throw new IllegalArgumentException("a path must be specified")
}

//class Lexicon {
//
//
//}
