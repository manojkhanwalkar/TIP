import util.JWUtil;

public class StaticKeyCreator {

    public static void main(String[] args) {

       var key =  JWUtil.createKey();
       JWUtil.writeToFile(key,"/home/manoj/IdeaProjects/TIP/src/main/resources/key.json");



    }
}
