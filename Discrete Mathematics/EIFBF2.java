import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Queue;

import java.util.*;

public class EIFBF2 {
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
			vertexList[i].index = i;
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();

			vertexList[u - 1].addAdjecentVertex(vertexList[v - 1]);
			vertexList[v - 1].addAdjecentVertex(vertexList[u - 1]);
		}
		return vertexList;
	}

	public static void bfs(Vertex[] vertexList) {
		HashMap<Integer, Integer[]> outputMap = new HashMap<Integer, Integer[]>();
		List<Group> groupList = new ArrayList<Group>();
		for (Vertex i : vertexList) {
			int male = 0, female = 0;
			Group group = new Group();
			if (!i.discovered) {
				Queue<Vertex> queue = new ArrayDeque<Vertex>();
				queue.add(i);
				i.discovered = true;
				while (!queue.isEmpty()) {
					Vertex w = queue.poll();
					group.vertexList.add(w.id);
					if (w.gender.equals("Nam")) {
						male++;
					} else
						female++;
					for (Vertex v : w.adjecentVertex) {
						if (!v.discovered) {
							queue.add(v);
							v.discovered = true;
						}
					}
				}
			}
			Integer[] outputArray = new Integer[2];
			outputArray[0] = male;
			outputArray[1] = female;
			for (Integer v : group.vertexList) {
				outputMap.put(v, outputArray);
			}
		}
		for (Vertex v : vertexList) {
			sb.append(v.id).append(" ");
			Integer[] output = outputMap.get(v.id);
			sb.append(output[0]).append(" ").append(output[1]).append("\n");

		}
	}

	static class Group {
		public HashSet<Integer> vertexList = new HashSet<Integer>();
		public int male = 0;
		public int female = 0;

		public Group() {
		}

		public void addVertex(Integer v) {
			this.vertexList.add(v);
		}

	}

	static class Vertex {
		public int index;
		public String gender;
		public int id;
		public boolean discovered;
		public HashSet<Vertex> adjecentVertex = new HashSet<Vertex>();

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
