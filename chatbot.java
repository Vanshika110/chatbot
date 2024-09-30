import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class chatbot {
    private static final String url = "https://api.openai.com/v1/chat/completions";
    private static final String key = "sk-A-oTtmSBof2rF2_zXSVBoiqUOfNly4OgRWnVMbb7WUT3BlbkFJKoByLGiBZ04QDserWEPGMf7U6zjGmL8TnbJc7ofJ8A";

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Ask the chatbot a question");
        String user = r.readLine();
        String res = getChatbotRes(user);
        System.out.println("chatbot response: " + res);
    }

    private static String getChatbotRes(String mess) throws IOException{
        URL u=new URL(url);
        HttpURLConnection conn= (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization","Bearer"+ key);
        conn.setDoOutput(true);
        String jsonIp="{\"model\": \"gpt-4\", \"message\": [{\"role\": \"user\", \"content\": \"" + mess+ "\"}], \"max_tokens\": 100}";
        try(OutputStream os=conn.getOutputStream()){
            byte[] ip=jsonIp.getBytes(StandardCharsets.UTF_8);
            os.write(ip,0,ip.length);
        }
        StringBuilder res= new StringBuilder();
        try(BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),StandardCharsets.UTF_8))){
            String resL;
            while((resL=br.readLine())!=null){
                res.append(resL.trim());
            }
        }
        String resS=res.toString();
        int conS=resS.indexOf("\"content\":\"")+11;
        int conE= resS.indexOf("\"",conS);
        return resS.substring(conS,conE);
    }
}
