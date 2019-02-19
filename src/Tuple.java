import java.util.StringTokenizer;

public class Tuple {
	int trainNo;
	String trainName;
	int seq;
	String stnCode;
	String stnName;
	String arrTime;
	String deptTime;
	int distance;
	String srcStn;
	String srcStnName;
	String destStn;
	String destStnName;

	public Tuple() {
	}

	public void parse(String text) {
		StringTokenizer itr = new StringTokenizer(text, ",");
		if (itr.countTokens() == 12) {
			this.trainNo = Integer.parseInt(itr.nextToken());
			this.trainName = itr.nextToken();
			this.seq = Integer.parseInt(itr.nextToken());
			this.stnCode = itr.nextToken();
			this.stnName = itr.nextToken();
			this.arrTime = itr.nextToken();
			this.deptTime = itr.nextToken();
			this.distance = Integer.parseInt(itr.nextToken());
			this.srcStn = itr.nextToken();
			this.srcStnName = itr.nextToken();
			this.destStn = itr.nextToken();
			this.destStnName = itr.nextToken();
		}
	}

	@Override
	public String toString() {
		return String.join(",", String.valueOf(trainNo), trainName,
				String.valueOf(seq), stnCode, stnName, arrTime, deptTime,
				String.valueOf(distance), srcStn, srcStnName, destStn,
				destStnName);
	}
}
