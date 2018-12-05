import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsExam {

	public static void main(String[] args) {
		
		if(args.length != 2) {
			System.out.println("Usage: HdfsExam <fineName> <content>");
			System.exit(-1);
		}

		try {
			Configuration conf = new Configuration();
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path(args[0]);
			
			if(hdfs.exists(path)) {
				hdfs.delete(path, true);
			}
			
			FSDataOutputStream os = hdfs.create(path);
			os.writeUTF(args[1]);
			os.close();
			
			FSDataInputStream is = hdfs.open(path);
			String inputString = is.readUTF();
			is.close();
			System.out.println("Input Text : " + inputString + "\n");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
