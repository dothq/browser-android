import { MaterialYouService } from "@assembless/react-native-material-you";
import React from "react";
import { Appearance, Pressable, SafeAreaView, ScrollView, StatusBar, Text, useColorScheme, View } from "react-native";
import { Button } from "react-native-paper";
import SystemNavigationBar from "react-native-system-navigation-bar";

export class Browser extends React.Component {
    public get androidTheme() {
        console.log(Appearance.getColorScheme())
        return Appearance.getColorScheme();
    }

    public componentDidMount(): void {
        SystemNavigationBar.setNavigationColor(0xff00ff00)
    }

    public render() {
        return (
            <SafeAreaView>
                <View>
                    <StatusBar
                        barStyle={this.androidTheme == "dark" ? "light-content" : "dark-content"}
                        backgroundColor={"rgba(0, 0, 0, 0)"}
                    />
                    <ScrollView>
                        <Text>Hello world</Text>
                        <Text>Hello world</Text>
                        <Text>Hello world</Text>
                        <Text>Hello world</Text>
                        <Text>Hello world</Text>
                        <Text>Hello world</Text>
                        <Text>Hello world</Text>
                        <Button color={"#0b57d0"} mode="contained" onPress={() => console.log('Pressed')}>
                            Press me
                        </Button>
                    </ScrollView>
                </View>
            </SafeAreaView>
        )
    }
}