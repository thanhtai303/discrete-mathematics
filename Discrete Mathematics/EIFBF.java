import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EIFBF {
	static InputReader reader;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Vertex[] vertexList = makeGraph();
		bfs(vertexList);
		System.out.println(sb);
	}

	public static Vertex[] makeGraph() {
		int numberOfVertexs = reader.nextInt();
		int numberOfEdges = reader.nextInt();
		Vertex[] vertexList = new Vertex[numberOfVertexs];
		for (int i = 0; i < numberOfVertexs; i++) {
			vertexList[i] = new Vertex(i + 1);
			vertexList[i].gender = reader.next();
			vertexList[i].index=i;
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();

			vertexList[u - 1].addAdjecentVertex(vertexList[v - 1]);
			vertexList[v - 1].addAdjecentVertex(vertexList[u - 1]);
		}
		for (Vertex i : vertexList) {
			i.adjecentVertex.sort((v1, v2) -> Integer.compare(v1.id, v2.id));
		}
		return vertexList;
	}

	public static void bfs(Vertex[] vertexList) {
		HashMap<Integer, List<Vertex>> outputMap=new HashMap<Integer, List<Vertex>>();
		for (Vertex i : vertexList) {
			if (!i.discovered) {
				int count = 0;
				List<Vertex> outputList = new ArrayList<Vertex>();
				Queue<Vertex> queue = new ArrayDeque<Vertex>();
				queue.add(i);
				i.discovered = true;
				while (!queue.isEmpty()) {
					Vertex w = queue.poll();
					count++;
					outputList.add(w);
					for (Vertex v : w.adjecentVertex) {
						if (!v.discovered) {
							queue.add(v);
							v.discovered = true;
						}
					}

				}
				if (count == vertexList.length) {
					sb.append(vertexList[vertexList.length-1]).append(" ");
					int countMale = 0;
					int countFemale = 0;
					for (Vertex v : vertexList) {
						if (v.gender.equals("Nam")) {
							countMale++;
						} else
							countFemale++;

					}
					sb.append(countMale).append(" ").append(countFemale);
				} else {
					
					Vertex presentVertex = outputList.get(0);
					for (Vertex v : outputList) {
						if (presentVertex.index < v.index) {
							presentVertex = v;
						}
					}
					outputMap.put(presentVertex.id, outputList);
					
				}
			}
		}
		List<Integer> outputList=new ArrayList<Integer>(outputMap.keySet());
		outputList.sort((v1, v2) -> Integer.compare(v1, v2));
		for(Integer i:outputList) {
			sb.append(i).append(" ");
			int countMale = 0;
			int countFemale = 0;
			for (Vertex v : outputMap.get(i)) {
				if (v.gender.equals("Nam")) {
					countMale++;
				} else
					countFemale++;

			}
			sb.append(countMale).append(" ").append(countFemale).append("\n");
		}
	}

	static class Vertex {
		public int index;
		public String gender;
		public int id;
		public boolean discovered;
		public List<Vertex> adjecentVertex = new ArrayList<Vertex>();

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecentVertex(Vertex vertex) {
			this.adjecentVertex.add(vertex);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.id);
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
