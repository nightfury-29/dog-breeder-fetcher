package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */

public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private BreedFetcher breedFetcher;
    private HashMap<String, List<String>> cachedBreeds = new HashMap<>();

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.breedFetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        // return statement included so that the starter code can compile and run.
        if  (cachedBreeds.containsKey(breed)) {
            return cachedBreeds.get(breed);
        }

        try {
            List<String> subBreeds = breedFetcher.getSubBreeds(breed);
            cachedBreeds.put(breed, subBreeds);
            this.callsMade++;
            return subBreeds;
        }

        catch (BreedNotFoundException e) {
            this.callsMade++;
            throw new BreedNotFoundException(breed);
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}