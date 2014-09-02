package com.haw.projecthorse.score;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.haw.projecthorse.score.json.Score;
import com.haw.projecthorse.score.json.ScoreImpl;

public class ScoreManagerImpl implements ScoreManager {
	private static final String scoreDirectory = "score/";
	private static final FilenameFilter scoresFilter = new FilenameFilter() {
		
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".score");
		}
	};
	private Map<Integer, Score> scores = new HashMap<Integer, Score>();
	private Integer loadedScoreID = null;
	
	public ScoreManagerImpl() {
		// load saved scores
		loadScores();
	}
	
	private void loadScores() {
		Json json = new Json();
		Score score = null;
		FileHandle dir = Gdx.files.local(scoreDirectory);
		
		for (FileHandle file : dir.list(scoresFilter)) {
			score = json.fromJson(ScoreImpl.class, file);
			scores.put(score.getID(), score);
		}
	}
	
	
	@Override
	public Score getLoadedScore() {
		if (loadedScoreID == null) {
			return null;
		}
		return scores.get(loadedScoreID);
	}

	@Override
	public Score loadScore(int scoreID) {
		if (!scoreExists(scoreID)) {
			scores.put(scoreID, new ScoreImpl(scoreID));
		}
		
		loadedScoreID = scoreID;
		return getLoadedScore();
	}

	@Override
	public void saveLoadedScore() {
		Json json = new Json();
		FileHandle scoreFile = Gdx.files.local(scoreDirectory).child(loadedScoreID + ".score");
		scoreFile.writeString(json.prettyPrint(getLoadedScore()), false);
	}

	@Override
	public boolean scoreExists(int scoreID) {
		return scores.containsKey(scoreID);
	}
}
