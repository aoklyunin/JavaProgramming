
package com.alexey.samsung;

import com.vk.api.sdk.objects.AuthResponse;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by aokly on 25.09.2016.
 */


public class VkHelper {
    public static final String APP_ID = "5229876";
    public static final String ACCESS_TOKEN = "1f1e0beb1f47d99c4c2f2477f90f589de3cfd096aecb5e91741acf86aa452a0aa48636a3e2a252b1803be";
    public static void getAccessToken() {
        try {
            VkApi.with(APP_ID, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void save(String userId, int from, int to) throws IOException {
        VkApi vkApi = VkApi.with(APP_ID, ACCESS_TOKEN);

        final int count = 200;
        int offset = from;
        while (offset < to) {
            System.out.println(offset);
            String text;
            while (true) {
                text = vkApi.getHistory(userId, offset, count, true);
                if (!text.contains("Too many requests per second")) break;
                System.out.println("Wait 1 second");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(text);
            offset += count;
        }
    }
}
