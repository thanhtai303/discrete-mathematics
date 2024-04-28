import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class EIUDEG {
	static InputReader sc = new InputReader(System.in);

	public static void main(String[] args) {
		Vertex[] graph = makeGraph();
		StringBuilder sbOut = new StringBuilder();
		for (int i =1; i < graph.length; i++) {
			Vertex vertex = graph[i];
			sbOut.append(vertex).append(" ");
		}
		System.out.println(sbOut);

	}

	public static Vertex[] makeGraph() {
		int nVertex = sc.nextInt();
		int mEdge = sc.nextInt();
		Vertex[] vertexes = new Vertex[nVertex + 1];
		for (int i = 1; i <= nVertex; i++) {
			vertexes[i] = new Vertex(i);
		}
		for (int i = 1; i <= mEdge; i++) {
			int u = sc.nextInt();
			int v = sc.nextInt();
			vertexes[u].addAdjecentVertex(vertexes[v]);
			vertexes[v].addAdjecentVertex(vertexes[u]);
		}
		return vertexes;

	}

	static class Vertex {
		public int id;
		public List<Vertex> adjecentVertex = new ArrayList<Vertex>();

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecentVertex(Vertex vertex) {
			this.adjecentVertex.add(vertex);
		}

		public int getNumberOfAdjecentVertex() {
			return this.adjecentVertex.size();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.adjecentVertex.size());
			return sb.toString();
		}

	}

	static class InputReader {

		StringTokenizer tokenizer;
		BufferedReader reader;
		String token;
		String temp;

		public InputReader(InputStream stream) {
			tokenizer = null;
			reader = new BufferedReader(new InputStreamReader(stream));
		}

		public InputReader(FileInputStream stream) {
			tokenizer = null;
			reader = new BufferedReader(new InputStreamReader(stream));
		}

		public String nextLine() throws IOException {
			return reader.readLine();
		}

		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					if (temp != null) {
						tokenizer = new StringTokenizer(temp);
						temp = null;
					} else {
						tokenizer = new StringTokenizer(reader.readLine());
					}
				} catch (IOException e) {
				}
			}
			return tokenizer.nextToken();
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
