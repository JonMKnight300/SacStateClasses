import React  from "react";
import { View, StyleSheet } from "react-native";
import AppText from "../components/AppText";
import colors from "../config/colors";
import ChartInterfaceScreenWMenu from "./ChartInterfaceScreenWMenu"

function ChartInterfaceScreen({ STtitle, STprice, STpercentage, symbol }) {

  return (
    <View>
      <View style={styles.detailsContainer}>
        <AppText style={styles.title}>{STtitle}</AppText>

        <AppText style={styles.price}>
          Today: {STprice} ({STpercentage})
        </AppText>
    </View>
    <ChartInterfaceScreenWMenu symbol={symbol} />
    </View>
  );
}


const styles = StyleSheet.create({
  detailsContainer: {
    padding: 20,
    marginTop: 30,
  },
  price: {
    color: colors.positive,
    fontWeight: "bold",
    fontSize: 20,
    marginVertical: 5,
  },
  title: {
    color: colors.black,
    fontSize: 26,
    fontWeight: "500",
    marginTop: 8,
  },
});

export default ChartInterfaceScreen;
