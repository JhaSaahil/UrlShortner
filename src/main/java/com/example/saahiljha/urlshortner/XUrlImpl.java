package com.example.saahiljha.urlshortner;

import java.util.*;
import java.util.stream.Collectors;

//XUrlImpl Class implementing XUrl
public class XUrlImpl implements XUrl {
    private Map<String,String> map;                     /* Map to store longUrl and shortUrl Mappings */
    private Map<String,Integer> HitCount;               /* Map to store HitCount for all longUrls */

    public XUrlImpl(){                                  /* Constructor Initialising */
        map = new HashMap<>();
        HitCount = new HashMap<>();
    }

    private static Set<String> getKeysJava8(            /* Function to get Key based on Value from Map */
      Map<String, String> map, String value) {

      return map
              .entrySet()
              .stream()
              .filter(entry -> Objects.equals(entry.getValue(), value))
              .map(Map.Entry::getKey)
              .collect(Collectors.toSet());

    }

    private final static String usingRandom(){                 /* Function to generate random String */     
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String allCharacters = alphabetsInUpperCase + alphabetsInLowerCase + numbers;

        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for(int i=0;i<9;i++){
            int randomIndex = random.nextInt(allCharacters.length());
            randomString.append(allCharacters.charAt(randomIndex));
        }

        return randomString.toString();
    }

    public String registerNewUrl(String longUrl){       /* Function to register new longUrl */

        if(map.containsKey(longUrl))  return map.get(longUrl);
        
        StringBuilder shortUrl = new StringBuilder();
        shortUrl.append("http://short.url/");

        String randomString = usingRandom();
        shortUrl.append(randomString);

        map.put(longUrl, shortUrl.toString());
        HitCount.put(longUrl, 0);

        return shortUrl.toString();
    }

    public String registerNewUrl(String longUrl, String shortUrl){      /* Function for mapping custom shortUrl */
        if(map.containsValue(shortUrl)) 
            return null;
        
        map.put(longUrl, shortUrl);
        HitCount.put(longUrl, 0);
        
        return shortUrl;
    }

    public String getUrl(String shortUrl){              /* Function to get corresponding longUrl */
        
        for (String key : getKeysJava8(map, shortUrl)) {
            int count = HitCount.get(key);
            count++;
            HitCount.put(key,count);
            return key;
        }

        return null;
    }

    public Integer getHitCount(String longUrl){         /* Function to check lookup for longUrl */
        if(map.containsKey(longUrl)) return HitCount.get(longUrl);
        HitCount.put(longUrl, 0);
        return 0;
    }

    public String delete(String longUrl){               /* Function for deleting longUrl */
        if(map.containsKey(longUrl))
            map.remove(longUrl);
        return null;
    }
}
