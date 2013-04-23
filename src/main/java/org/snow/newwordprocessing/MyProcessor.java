package org.snow.newwordprocessing;

import com.mongodb.*;
import org.snow.tokenizer.IKAnalyzer;
import org.snow.util.Props;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: snowhyzhang
 * Date: 13-4-22
 * Time: 下午10:37
 * To change this template use File | Settings | File Templates.
 */
public class MyProcessor {
    private WordsProcessor wordsProcessor;

    public MyProcessor() {
        wordsProcessor = new WordsProcessor();
    }

    public static void main(String[] args) {
        MyProcessor myProcessor = new MyProcessor();
        Set<String> newWords = myProcessor.getNewWords();
        System.out.println("+++++++++++++++++");
        System.out.println("Result: ");
        for (String word: newWords){
            System.out.println(word);
        }
    }

    public void wordsSource(){
        Mongo mongo = null;
        try {
            MongoOptions options = new MongoOptions();
            options.threadsAllowedToBlockForConnectionMultiplier = 3000;
            options.connectionsPerHost = 300;
            mongo = new Mongo(new ServerAddress(Props.getProperty("mongoHost"),
                    Integer.parseInt(Props.getProperty("mongoPort"))), options);
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (mongo == null) {
            return;
        }

        DB mongoDB = mongo.getDB(Props.getProperty("mongoDB"));
        DBObject query = new BasicDBObject();
        DBObject field = new BasicDBObject();

        field.put("name", 1);
        DBCollection collection = mongoDB.getCollection("product");
        DBCursor cursor = collection.find(query, field);
        int currentRow = 0;
        int maxRow = 10000;
        String para;
        List<DBObject> resultList = new ArrayList<DBObject>();
        while (cursor.hasNext() && currentRow < maxRow) {
            DBObject dbObject = cursor.next();
            resultList.add(dbObject);
            ++currentRow;
        }
        cursor.close();
        System.out.println("Processing new words:");
        for (DBObject object: resultList){
            para = (String)object.get("name");
            wordsProcessor.getWordsSource(IKAnalyzer.tokenizer(para));
        }
    }

    public Set<String> getNewWords(){
        wordsSource();
        return wordsProcessor.processNewWords();
    }
}
