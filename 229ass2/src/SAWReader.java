//import bos.Pair;

import java.io.IOException;
//import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SAWReader {
    List<String> contents;
    List<bos.Pair<Integer, Integer>> blocks;

    public SAWReader(String filename) {
        try {
            contents = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(filename));
        } catch (IOException e) {
            contents = new ArrayList();
        }
    }

    public bos.Pair<Integer, Integer> getSheepLoc() {
        return searchForPair("sheep");
    }

    public bos.Pair<Integer, Integer> getWolfLoc() {
        return searchForPair("wolf");
    }

    public bos.Pair<Integer, Integer> getShepherdLoc() {
        return searchForPair("shepherd");
    }

    private bos.Pair<Integer,Integer> searchForPair(String target){
        for (String s: contents){
            Pattern p = Pattern.compile(target + ":\\s*\\((\\d*),\\s*(\\d)\\)");
            Matcher m = p.matcher(s);
            if(m.matches()){
                return new bos.Pair( Integer.parseInt(m.group(2).trim()), Integer.parseInt(m.group(2).trim()));
            }
        }
        return new bos.Pair(0,0);
    }

    public List<bos.Pair<Integer, Integer>> getBlocksLoc() {
        blocks = new ArrayList<bos.Pair<Integer, Integer>>();

        for(String s: contents){
            Pattern p = Pattern.compile("block:\\s*\\((\\d*),\\s*(\\d*)\\)");
            Matcher m =p.matcher(s);
            if (m.matches()){
                blocks.add(new bos.Pair(Integer.parseInt(m.group(2).trim()), Integer.parseInt(m.group(2).trim())));
            }
        }
        return blocks;
    }




}
