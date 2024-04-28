import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EIUDFS1 {
	static InputReader reader;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Vertex[] vertexes = makeDirectedGraph();

		breadthFirstSearch(vertexes[0]);

		System.out.println(sb);
	}

	public static Vertex[] makeGraph() {
		int numberOfVertexes = reader.nextInt();
		int numberOfEdges = reader.nextInt();
		Vertex[] listOfVertex = new Vertex[numberOfVertexes];
		for (int i = 0; i < listOfVertex.length; i++) {
			listOfVertex[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();
			listOfVertex[u].addAdjecentVertex(listOfVertex[v]);
			listOfVertex[v].addAdjecentVertex(listOfVertex[u]);
		}
		for (Vertex i : listOfVertex) {
			i.adjecentVertex.sort((v1, v2) -> Integer.compare(v1.id, v2.id));
		}
		return listOfVertex;
	}
	public static Vertex[] makeDirectedGraph() {
		int numberOfVertexes = reader.nextInt();
		int numberOfEdges = reader.nextInt();
		Vertex[] listOfVertex = new Vertex[numberOfVertexes];
		for (int i = 0; i < listOfVertex.length; i++) {
			listOfVertex[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();
			listOfVertex[u].addAdjecentVertex(listOfVertex[v]);
		}
		for (Vertex i : listOfVertex) {
			i.adjecentVertex.sort((v1, v2) -> Integer.compare(v1.id, v2.id));
		}
		return listOfVertex;
	}

	public static void depthFirstSearch(Vertex v) {
		v.discovered = true;
		sb.append(v.id).append(" ");
		for (Vertex i : v.adjecentVertex) {
			if (!i.discovered) {
				depthFirstSearch(i);
			}
		}

	}
	

	public static void breadthFirstSearch(Vertex v) {
		Queue<Vertex> vertexQueue = new ArrayDeque<Vertex>();
		vertexQueue.add(v);
		v.discovered = true;
		while (!vertexQueue.isEmpty()) {
			Vertex w=vertexQueue.poll();
			sb.append(w.id).append(" ");
			for (Vertex i : w.adjecentVertex) {
				if (!i.discovered) {
					vertexQueue.add(i);
					i.discovered = true;

				} 
			}
		}
	}

	static class Vertex {
		public int id;
		public boolean discovered;
		public List<Vertex> adjecentVertex = new ArrayList<Vertex>();

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecentVertex(Vertex vertex) {
			this.adjecentVertex.add(vertex);
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
