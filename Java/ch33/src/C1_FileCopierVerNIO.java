
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class C1_FileCopierVerNIO {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.print("대상 파일: ");
		Path src =  Paths.get(sc.nextLine());
		System.out.print("사본 파일: ");
		Path des =  Paths.get(sc.nextLine());	
	
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		try(FileChannel ifc = FileChannel.open(src, StandardOpenOption.READ);
				FileChannel ofc = FileChannel.open(des, StandardOpenOption.WRITE,
														StandardOpenOption.CREATE)){
			//int num;
			while(true) {
				//num = ifc.read(buf);
				//if(num == -1)	break;
				if(ifc.read(buf) == -1)	break;
				
				buf.flip();
				ofc.write(buf);
				buf.clear();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
