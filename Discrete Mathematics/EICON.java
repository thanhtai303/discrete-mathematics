import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.*;

public class EICON {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader=new InputReader(System.in);
		int numberOfVertexes = reader.nextInt();
		int numberOfEdges = reader.nextInt();
		int numberOfQuerries = reader.nextInt();
		Vertex[] graph = makeGraph(numberOfVertexes, numberOfEdges);
		checkQuerry(numberOfQuerries, graph);

	}

	public static Vertex[] makeGraph(int numberOfVertexes, int numberOfEdges) {
		Vertex[] listOfVertex = new Vertex[numberOfVertexes + 1];
		for (int i = 1; i < listOfVertex.length; i++) {
			listOfVertex[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();
			listOfVertex[v].addAdjecentVertex(listOfVertex[u]);
		}
		return listOfVertex;

	}

	public static void checkQuerry(int numberofQuerries, Vertex[] listOfVertexes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numberofQuerries; i++) {
			int a = reader.nextInt();
			int b = reader.nextInt();
			if (listOfVertexes[a].adjecentVertex.contains(listOfVertexes[b])) {
				sb.append("Y").append("\n");
			} else {
				sb.append("N").append("\n");
			}
		}
		System.out.println(sb);
		return;

	}

	public static class Vertex {
		public int id;
		public HashSet<Vertex> adjecentVertex = new HashSet<Vertex>();

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecentVertex(Vertex vertex) {
			this.adjecentVertex.add(vertex);
		}

		public int getID() {
			return this.id;
		}


		@Override
		public int hashCode() {
			return this.id;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Vertex) {
				return ((Vertex) obj).id == id;
			}
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
