package com.vefve.db.store.memory;

import com.vefve.db.store.Store;

public class MemoryStore<Key extends Comparable<Key>, Value> implements Store<Key, Value> {

	@Override
	public Value get(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean put(Key key, Value value) {
		// TODO Auto-generated method stub
		return false;
	}

}
