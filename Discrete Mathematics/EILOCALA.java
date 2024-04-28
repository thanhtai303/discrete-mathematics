import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class EILOCALA {
	static InputReader reader;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Vertex[] graph = makeGraph();
		System.out.println(sb);
	}

	public static Vertex[] makeGraph() {
		int numberOfVertex = reader.nextInt();
		Vertex[] graph = new Vertex[numberOfVertex];
		for (int i = 0; i < numberOfVertex; i++) {
			graph[i] = new Vertex(i);
		}
		for (int i = 1; i < numberOfVertex; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();
			int lenght = reader.nextInt();
			if (u < v) {
				Edge edge = new Edge(lenght, graph[u], graph[v]);
				graph[u].addNeighbour(edge);
				graph[v].addNeighbour(edge);
			} else {
				Edge edge = new Edge(lenght, graph[v], graph[u]);
				graph[u].addNeighbour(edge);
				graph[v].addNeighbour(edge);
			}
		}
		dfs(graph[0]);
		int max = 0;
		int temp = 0;
		for (int i = 0; i < numberOfVertex; i++) {
			if (graph[i].distance > max) {
				max = graph[i].distance;
				temp = i;
			} else if (max == graph[i].distance && temp > i) {
				temp = i;
			}
		}
		temp = dfs2(graph[temp], temp);
		max = 0;
		for (Vertex i : graph) {
			if (i.distance > max) {
				max = i.distance;
			}
		}
		sb.append(temp).append(" ").append(max);
		return graph;
	}

	public static int dfs2(Vertex v, int temp) {
		v.discovered2 = true;
		for (Edge i : v.neighbour) {
			if (!i.targetNode.discovered2) {
				i.targetNode.distance2 = i.lenght + v.distance2;
				i.targetNode.previous2 = v;
				dfs(i.targetNode);
			}
			if (i.targetNode.discovered2) {
				if (i.targetNode.previous2 != null && i.targetNode.previous2.id != v.id) {
					if (i.lenght + v.distance2 > i.targetNode.distance2) {
						i.targetNode.distance2 = i.lenght + v.distance2;
					}
				}
			}
			if (i.targetNode.neighbour.isEmpty() && i.targetNode.id < v.id) {
				temp = i.targetNode.id;
			}
		}
		return temp;
	}

	public static void dfs(Vertex v) {
		v.discovered = true;
		for (Edge i : v.neighbour) {
			if (!i.targetNode.discovered) {
				i.targetNode.distance = i.lenght + v.distance;
				i.targetNode.previous = v;
				dfs(i.targetNode);
			}
			if (i.targetNode.discovered) {
				if (i.targetNode.previous != null && i.targetNode.previous.id != v.id) {
					if (i.lenght + v.distance > i.targetNode.distance) {
						i.targetNode.distance = i.lenght + v.distance;
					}
				}
			}
		}
	}

	static class Edge {
		public int lenght;
		public Vertex startNode;
		public Vertex targetNode;

		public Edge(int lenght, Vertex startNode, Vertex targetNode) {
			this.lenght = lenght;
			this.startNode = startNode;
			this.targetNode = targetNode;
		}

	}

	static class Vertex {
		public Vertex previous2;
		public boolean discovered2;
		public int distance2;
		public int id;
		public boolean discovered;
		public List<Edge> neighbour = new ArrayList<Edge>();
		public Vertex previous;
		public int distance;

		public Vertex(int id) {
			this.id = id;
		}

		public void addNeighbour(Edge i) {
			this.neighbour.add(i);
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
