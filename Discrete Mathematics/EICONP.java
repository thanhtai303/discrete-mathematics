import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EICONP {

	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Vertex[] vertexList = makeGraph();
		checkComponents(vertexList);
	}

	public static Vertex[] makeGraph() {
		int numberOfVertexs = reader.nextInt();
		int numberOfEdges = reader.nextInt();
		Vertex[] vertexList = new Vertex[numberOfVertexs];
		for (int i = 0; i < numberOfVertexs; i++) {
			vertexList[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();

			vertexList[u].addAdjecentVertex(vertexList[v]);
			vertexList[v].addAdjecentVertex(vertexList[u]);
		}
		for (Vertex i : vertexList) {
			i.adjecentVertex.sort((v1, v2) -> Integer.compare(v1.id, v2.id));
		}
		return vertexList;
	}

	public static void depthFirstSearch(Vertex v, int count) {
		v.discovered = true;
		count++;
		for (Vertex i : v.adjecentVertex) {
			if (!i.discovered) {
				depthFirstSearch(i, count);
			}
		}

	}

	public static void breadthFirstSearch(Vertex v, int count) {
		Queue<Vertex> vertexQueue = new ArrayDeque<Vertex>();
		vertexQueue.add(v);
		v.discovered = true;
		while (!vertexQueue.isEmpty()) {
			Vertex w = vertexQueue.poll();
			count++;
			for (Vertex i : w.adjecentVertex) {
				if (!i.discovered) {
					vertexQueue.add(i);
					i.discovered = true;
				}
			}
		}
	}

	public static void checkComponents(Vertex[] vertexList) {
		StringBuilder sb = new StringBuilder();
		for (Vertex v : vertexList) {
			if (!v.discovered) {
				List<Vertex> componentVertexs = new ArrayList<Vertex>();
				int countEdges = 0;
				Queue<Vertex> vertexQueue = new ArrayDeque<Vertex>();
				vertexQueue.add(v);
				v.discovered = true;
				while (!vertexQueue.isEmpty()) {
					Vertex w = vertexQueue.poll();
					componentVertexs.add(w);
					for (Vertex i : w.adjecentVertex) {
						if (!i.discovered) {
							vertexQueue.add(i);
							i.discovered = true;
						}
					}
				}
				if (componentVertexs.size() == vertexList.length) {
					sb.append(v.id).append(" ").append(vertexList.length).append(" ");
					for (Vertex i : vertexList) {
						countEdges += i.adjecentVertex.size() ;
					}
					sb.append(countEdges/2 );
					break;
				} else {
					sb.append(v.id).append(" ").append(componentVertexs.size()).append(" ");
					for (Vertex i : componentVertexs) {
						countEdges += i.adjecentVertex.size() ;
					}
					sb.append(countEdges/2 ).append("\n");
				}
			}
		}
		System.out.println(sb);
	}

	static class Vertex {
		public int id;
		public boolean discovered;
		public List<Vertex> adjecentVertex = new ArrayList<Vertex>();

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecentVertex(Vertex v) {
			this.adjecentVertex.add(v);
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
