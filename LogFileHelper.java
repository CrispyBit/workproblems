import java.util.*;

public class LogFileHelper {

    public List<String> sortLogData(List<String> rawLogData){
        List<String> sortedData = null;

        if(rawLogData != null){
            sortedData = new ArrayList<>(rawLogData.size());

            Map<String, String> alphaEntries = new HashMap<>();
            Map<String, List<String>> alphaEntryKeysSortedByEntry = new TreeMap<>();
            Queue<String> numericEntries = new LinkedList<>();

            //For every log entry
            for(String entry : rawLogData){

                //Tokenize the entry [key] [log data (split by spaces)]
                String[] tokenizedEntry = entry.split("\\s+");

                try {
                    //Check to see if first log entry data is numeric
                    Long.parseLong(tokenizedEntry[1]);

                    //If so add it to the numeric entries queue
                    numericEntries.add(entry);
                } catch (NumberFormatException nfe) {

                    //Entry is alpha numeric, break out the key and the log data
                    String logEntryKey = tokenizedEntry[0];
                    String logEntry = Arrays.toString(Arrays.copyOfRange(tokenizedEntry, 1, tokenizedEntry.length-1));

                    //Add the log entry to the entryKey -> entry map for later
                    alphaEntries.put(logEntryKey, entry);

                    //Check to see if we have already added the entry to the entry -> entryKey list map
                    List<String> entryKeys = alphaEntryKeysSortedByEntry.getOrDefault(logEntry, null);

                    if(entryKeys == null){

                        //If not, create a new entryKey list and add it to the entry -> entryKey list map
                        entryKeys = new ArrayList<>();
                        alphaEntryKeysSortedByEntry.put(logEntry, entryKeys);
                    }

                    //Add the log entry key to the key list
                    entryKeys.add(logEntryKey);
                }
            }

            //All entries processed, now build the sorted result list

            //Alphanumeric entries go first, get the already sorted log entries (TreeMap)
            for(String entry : alphaEntryKeysSortedByEntry.keySet()){

                //Get the keys associated with the entry
                List<String> entryKeys = alphaEntryKeysSortedByEntry.get(entry);

                //Sort the keys in case there is more than one key for the entry
                // (Assumption: list with one entry will be sorted by sort method without exception)
                Collections.sort(entryKeys);

                //For every entry key, get the associated entry from the entryKey -> entry map
                //NOTE: Could have also appended strings here but to reduce operational complexity (creating new strings)
                //I decided to use more space by keeping the pointers to the entries in this map.
                for(String entryKey : entryKeys){
                    sortedData.add(alphaEntries.get(entryKey));
                }
            }

            //Append numeric entries to end (Queue to keep original ordering)
            sortedData.addAll(numericEntries);
        }

        return sortedData;
    }
}
