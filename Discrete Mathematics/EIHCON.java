import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.TreeSet;

public class EIHCON {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		int numberOfVertexs = reader.nextInt();
		int numberOfEdges = reader.nextInt();
		int numberOfQuerries = reader.nextInt();

		Vertex[] vertexList = makeGraph(numberOfVertexs, numberOfEdges);
		checkQuerries(numberOfQuerries, vertexList);
	}

	public static Vertex[] makeGraph(int numberOfVertexs, int numberOfEdges) {

		Vertex[] vertexList = new Vertex[numberOfVertexs + 1];
		for (int i = 1; i < vertexList.length; i++) {
			vertexList[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();

			vertexList[v].addAdjecentVertex(vertexList[u]);
		}
		return vertexList;
	}

	public static void checkQuerries(int numberOfQuerries, Vertex[] vertexList) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numberOfQuerries; i++) {
			int a = reader.nextInt();
			int b = reader.nextInt();

			if (vertexList[a].adjecentVertexs.contains(vertexList[b])) {
				sb.append("Y").append("\n");
			} else {
				if (vertexList[a].check(vertexList[b])) {
					sb.append("Y").append("\n");
				} else
					sb.append("N").append("\n");
			}
		}
		System.out.println(sb);
	}

	static class Vertex {
		public int id;
		public HashSet<Vertex> adjecentVertexs = new HashSet<Vertex>();

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecentVertex(Vertex v) {
			this.adjecentVertexs.add(v);
		}

		public boolean check(Vertex b) {
			for (Vertex v : this.adjecentVertexs) {
				if (v.adjecentVertexs.contains(b)) {
					return true;
				}
			}
			return false;
		}

		public void sortHashSet() {
			List<Vertex> vertexListSort = new ArrayList<Vertex>(this.adjecentVertexs);
			vertexListSort.sort((v1, v2) -> Integer.compare(v1.id, v2.id));
			this.adjecentVertexs = new HashSet<Vertex>(vertexListSort);

		}

		@Override
		public int hashCode() {
			return this.id;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Vertex)
				return ((Vertex) obj).id == id;
			return false;
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
