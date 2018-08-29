# VefveDB

**A disk backed, memory based Key-Value store.**

### Using the DB.
- Initialize an object of type `com.vefve.db.VefveDB`.
- Invoke `get` and `put` methods as required.

#### Example
```java
VefveDB<String, String> vefveDB = new VefveDB<String, String>(true, "/tmp/data/", 5L, 0.7f, 4);
vefveDB.put("www.cs.princeton.edu", "128.112.136.12");
vefveDB.get("www.cs.princeton.edu");
```

#### Possible configurations for initializing VefveDB
- `usePersistentStorage` - `boolean` - Whether to use persistent storage or not.
- `persistentStoragePath` - `String` - Location to store the data in.
- `memoryStorageSize` - `long` - Max size of the Memory store.
- `loadFactorThreshold` - `float` - Ranges between 0 and 1. The load factor after which the memory store is moved to disk store. Set this to 1, if you want to empty the memory store only when it's full.
- `branchingFactor` - `int` - Constraint: Must be even and greater than 2. Max children per B-tree node = Branching Factor - 1.

#### Assumptions and Trade-offs
- The key type must be `Serializable` and `Comparable`. The value type must be `Serializable`.
- Currently there is no delete functionality implemented.
- There is no max limit to the data that can be stored on the disk.
- Currently data is stored in the B-Tree nodes itself.
- The whole tree is locked when the data from Memory Store is moved to Disk Store.

#### Running Unit Tests
- Run `mvn test`
