import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EIFOLTRE {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		StringBuilder sb = new StringBuilder();
		List<File> fileList = makeTree();
		for (File i : fileList) {
			sb.append(i).append("\n");
		}
		System.out.println(sb);
	}

	public static List<File> makeTree() {
		int numberOfFile = reader.nextInt();
		HashMap<String, File> fileList = new HashMap<String, File>();
		for (int i = 0; i < numberOfFile - 1; i++) {
			String a = reader.next();
			String b = reader.next();
			if (fileList.containsKey(a) && fileList.containsKey(b)) {
				fileList.get(a).addSubFile(fileList.get(b));
				fileList.get(b).addSubFile(fileList.get(a));
			} else if (fileList.containsKey(a) && !fileList.containsKey(b)) {
				File fileb = new File(b);
				fileb.addSubFile(fileList.get(a));
				fileList.get(a).addSubFile(fileb);
				fileList.put(b, fileb);

			} else if (!fileList.containsKey(a) && fileList.containsKey(b)) {
				File filea = new File(a);
				filea.addSubFile(fileList.get(b));
				fileList.get(b).addSubFile(filea);
				fileList.put(a, filea);
			} else {
				File filea = new File(a);
				File fileb = new File(b);
				filea.addSubFile(fileb);
				fileb.addSubFile(filea);
				fileList.put(a, filea);
				fileList.put(b, fileb);

			}

		}

		String rootFile = reader.next();
		fileList.get(rootFile).level = 0;
		List<File> fileSort = new ArrayList<File>(fileList.values());
		for (File i : fileSort) {
			i.subFile.sort((f1, f2) -> f1.name.compareToIgnoreCase(f2.name));
		}
		List<File> output = new ArrayList<File>();
		return dfs(fileList.get(rootFile), output);
	}

	public static List<File> dfs(File f, List<File> output) {
		f.discovered = true;
		output.add(f);
		for (File i : f.subFile) {
			if (!i.discovered) {
				i.level = f.level + i.level + 1;
				dfs(i, output);
			}
		}
		return output;

	}

	static class File {
		public int id;
		public String name;
		public int level;
		boolean discovered;
		public List<File> subFile = new ArrayList<File>();

		public File(String name) {
			this.name = name;
		}

		public void addSubFile(File f) {
			this.subFile.add(f);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("-");
			for (int i = 0; i < this.level; i++) {
				sb.append("---");
			}
			sb.append(this.name);
			return sb.toString();
		}
	}

	static class InputReader {
		private byte[] inbuf = new byte[2 << 23];
		public int lenbuf = 0, ptrbuf = 0;
		public InputStream is;

		public InputReader(InputStream stream) throws IOException {

			inbuf = new byte[2 << 23];
			lenbuf = 0;
			ptrbuf = 0;
			is = System.in;
			lenbuf = is.read(inbuf);
		}

		public InputReader(FileInputStream stream) throws IOException {
			inbuf = new byte[2 << 23];
			lenbuf = 0;
			ptrbuf = 0;
			is = stream;
			lenbuf = is.read(inbuf);
		}

		public boolean hasNext() throws IOException {
			if (skip() >= 0) {
				ptrbuf--;
				return true;
			}
			return false;
		}

		public String nextLine() throws IOException {
			int b = skip();
			StringBuilder sb = new StringBuilder();
			while (!isSpaceChar(b) && b != ' ') { // when nextLine, ()
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		public String next() {
			int b = skip();
			StringBuilder sb = new StringBuilder();
			while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b
										// != ' ')
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		private int readByte() {
			if (lenbuf == -1)
				throw new InputMismatchException();
			if (ptrbuf >= lenbuf) {
				ptrbuf = 0;
				try {
					lenbuf = is.read(inbuf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (lenbuf <= 0)
					return -1;
			}
			return inbuf[ptrbuf++];
		}

		private boolean isSpaceChar(int c) {
			return !(c >= 33 && c <= 126);
		}

		private double nextDouble() {
			return Double.parseDouble(next());
		}

		public Character nextChar() {
			return skip() >= 0 ? (char) skip() : null;
		}

		private int skip() {
			int b;
			while ((b = readByte()) != -1 && isSpaceChar(b))
				;
			return b;
		}

		public int nextInt() {
			int num = 0, b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = num * 10 + (b - '0');
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}

		public long nextLong() {
			long num = 0;
			int b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = num * 10 + (b - '0');
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}
	}
}
