package client;

import java.util.HashMap;

import constants.ServerEnvironment;

public class EvanSkillPoints {
	private HashMap<Integer, Integer> skillPoints = new HashMap<>(10, 1.0F);

	public EvanSkillPoints() {
		this.skillPoints.put(Integer.valueOf(2200), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2210), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2211), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2212), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2213), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2214), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2215), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2216), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2217), Integer.valueOf(0));
		this.skillPoints.put(Integer.valueOf(2218), Integer.valueOf(0));
	}

	public HashMap<Integer, Integer> getSkillPoints() {
		return this.skillPoints;
	}

	public void addSkillPoints(int job, int points) {
		if (this.skillPoints.containsKey(Integer.valueOf(job))) {
			this.skillPoints.put(Integer.valueOf(job),
					Integer.valueOf(points + ((Integer) this.skillPoints.get(Integer.valueOf(job))).intValue()));
		}
	}

	public int getSkillPoints(int jobid) {
		if (this.skillPoints.containsKey(Integer.valueOf(jobid))) {
			return ((Integer) this.skillPoints.get(Integer.valueOf(jobid))).intValue();
		}
		return 0;
	}

	public String prepareSkillQuery(int id) {
		StringBuilder query = new StringBuilder(
				"INSERT INTO evan_skillpoints (characterid, evan1, evan2, evan3, evan4, evan5, evan6, evan7, evan8, evan9, evan10) VALUES (");
		query.append(id).append(", ").append(this.skillPoints.get(Integer.valueOf(MapleJob.EVAN2.getId())));
		for (int i = 2210; i < 2219; i++) {
			query.append(", ").append(this.skillPoints.get(Integer.valueOf(i)));
		}
		query.append(")");
		if(ServerEnvironment.isDebugEnabled()){
			System.out.println("Saving evanskill points: " + query);
		}
		return query.toString();
	}

	public void setSkillPoints(int job, int points) {
		if (this.skillPoints.containsKey(Integer.valueOf(job))) {
			this.skillPoints.put(Integer.valueOf(job), Integer.valueOf(points));
		}
	}

	@Override
	public String toString() {
		return "EvanSkillPoints [skillPoints=" + skillPoints + "]";
	}
	
	
}
