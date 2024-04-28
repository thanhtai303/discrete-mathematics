import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EIUMLMK2 {
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
		for (int i = 0; i < numberOfVertex - 1; i++) {
			int a = reader.nextInt();
			int b = reader.nextInt();

			graph[a].addAdjecent(graph[b]);
			graph[b].addAdjecent(graph[a]);
		}
		for (int i = 0; i < numberOfVertex; i++) {
			graph[i].maxPrice = reader.nextDouble();
		}
		int price = reader.nextInt();
		graph[0].priceToBuy = price;
		dfs(graph[0]);
		dfs2(graph[0]);
		for (Vertex i : graph) {
			sb.append(i.id).append(" ").append(i.priceToBuy).append(" ").append(i.maxPrice).append(" ")
					.append(i.productSale).append("\n");
		}
		return graph;
	}

	public static void dfs2(Vertex v) {
		v.discovered2 = true;
		for (Vertex i : v.neighbour) {
			if (!i.discovered2) {
				if (i.priceToBuy <= i.maxPrice) {
					i.productSale++;
					v.productSale--;
				}
				dfs(i);
			}
		}
	}

	public static void dfs(Vertex v) {
		v.discovered = true;
		if (v.id > 0) {
			v.productSale = v.neighbour.size() - 1;
		} else {
			v.productSale = v.neighbour.size();
		}
		for (Vertex i : v.neighbour) {
			if (!i.discovered) {
				i.priceToBuy += v.priceToBuy + Math.floor(v.priceToBuy * 0.1);
				dfs(i);
			}
		}
	}

	static class Vertex {
		public double priceToBuy;
		public int id;
		public List<Vertex> neighbour = new ArrayList<Vertex>();
		public boolean discovered2;
		public boolean discovered;
		public double maxPrice;
		public double percent;
		public int productUse;
		public int productSale;

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecent(Vertex v) {
			this.neighbour.add(v);
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
