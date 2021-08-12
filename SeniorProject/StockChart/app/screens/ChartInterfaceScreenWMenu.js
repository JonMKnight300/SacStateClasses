import React, { useEffect, useState }  from "react";
import { View, StyleSheet } from "react-native";
import AppText from "../components/AppText";
import TheChart from "../components/TheChartV3";
import colors from "../config/colors";

import { TouchableOpacity } from "react-native-gesture-handler";


function ChartInterfaceScreenWMenu({ symbol }) {


  var [ isPress, setIsPress ] = React.useState(false);
  const [isLoading, setLoading] = useState(true);
  const [data, setData] = useState([]);
  //console.log(data);

  var [ isPress2, setIsPress2 ] = React.useState(false);
  const [isLoading2, setLoading2] = useState(true);
  const [data2, setData2] = useState([]);
  //console.log(data2);

  var [ isPress3, setIsPress3 ] = React.useState(false);
  const [isLoading3, setLoading3] = useState(true);
  const [data3, setData3] = useState([]);
  //console.log(data3);

  var [ isPress4, setIsPress4 ] = React.useState(false);
  const [isLoading4, setLoading4] = useState(true);
  const [data4, setData4] = useState([]);
  //console.log(data4);

  var [ isPress5, setIsPress5 ] = React.useState(false);
  const [isLoading5, setLoading5] = useState(true);
  const [data5, setData5] = useState([]);
 // console.log(data5);

 const [whichArray, setWhichArray] = React.useState(data);

  useEffect(() => {
    fetch(`https://cloud.iexapis.com/stable/stock/${symbol}/chart/1d?token=pk_INSERT_TOKEN_VALUE `)
      .then((res) => res.json())
      .then((json) => setData(json.map(curEntry => (curEntry.close))))
      .catch((error) => {
        console.log("Error", error);
      })
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    fetch(`https://cloud.iexapis.com/stable/stock/${symbol}/chart/1w?token=pk_INSERT_TOKEN_VALUE `)
      .then((res) => res.json())
      .then((json) => setData2(json.map(curEntry => (curEntry.close))))
      .catch((error) => {
        console.log("Error", error);
      })
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    fetch(`https://cloud.iexapis.com/stable/stock/${symbol}/chart/1m?token=pk_INSERT_TOKEN_VALUE `)
      .then((res) => res.json())
      .then((json) => setData3(json.map(curEntry => (curEntry.close))))
      .catch((error) => {
        console.log("Error", error);
      })
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    fetch(`https://cloud.iexapis.com/stable/stock/${symbol}/chart/4m?token=pk_INSERT_TOKEN_VALUE `)
      .then((res) => res.json())
      .then((json) => setData4(json.map(curEntry => (curEntry.close))))
      .catch((error) => {
        console.log("Error", error);
      })
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    fetch(`https://cloud.iexapis.com/stable/stock/${symbol}/chart/1y?token=pk_INSERT_TOKEN_VALUE `)
      .then((res) => res.json())
      .then((json) => setData5(json.map(curEntry => (curEntry.close))))
      .catch((error) => {
        console.log("Error", error);
      })
      .finally(() => setLoading(false));
  }, []);

  const changeArray1 = () => {
    setWhichArray(data);
    setIsPress(true);
    setIsPress2(false);
    setIsPress3(false);
    setIsPress4(false);
    setIsPress5(false);
    }
    const changeArray2 = () => {
      setWhichArray(data2);
      setIsPress(false);
      setIsPress2(true);
      setIsPress3(false);
      setIsPress4(false);
      setIsPress5(false);
      }
      const changeArray3 = () => {
        setWhichArray(data3);
        setIsPress(false);
        setIsPress2(false);
        setIsPress3(true);
        setIsPress4(false);
        setIsPress5(false);
        }
        const changeArray4 = () => {
          setWhichArray(data4);
          setIsPress(false);
          setIsPress2(false);
          setIsPress3(false);
          setIsPress4(true);
          setIsPress5(false);
          }
          const changeArray5 = () => {
            setWhichArray(data5);
            setIsPress(false);
            setIsPress2(false);
            setIsPress3(false);
            setIsPress4(false);
            setIsPress5(true);
            }

  return (

    <View>
      <View style={styles.timebarContainer}>
      <View style={isPress === false ? styles.timebarContainer : styles.pressed } >
        <TouchableOpacity onPress={() => changeArray1()}>
          <AppText style={styles.timebar}>1 day</AppText>
        </TouchableOpacity></View>
        <View style={isPress2 === false ? styles.timebarContainer : styles.pressed } >
        <TouchableOpacity onPress={() => changeArray2()}>
          <AppText style={styles.timebar}>1 week</AppText>
        </TouchableOpacity></View>
        <View style={isPress3 === false ? styles.timebarContainer : styles.pressed } >
        <TouchableOpacity onPress={() => changeArray3()}>
          <AppText style={styles.timebar}>1 month</AppText>
        </TouchableOpacity></View>
        <View style={isPress4 === false ? styles.timebarContainer : styles.pressed } >
        <TouchableOpacity onPress={() => changeArray4()}>
          <AppText style={styles.timebar}>4 month</AppText>
        </TouchableOpacity></View>
        <View style={isPress5 === false ? styles.timebarContainer : styles.pressed } >
        <TouchableOpacity onPress={() => changeArray5()}>
          <AppText style={styles.timebar}>1 year</AppText>
        </TouchableOpacity></View>
      </View>
      <TheChart style={styles.theChart} data={whichArray} />


    </View>

  );

}



const styles = StyleSheet.create({
  theChart: {
    width: "100%",
    justifyContent: "center",
  alignContent: "center",
  position: "absolute",
  },
  timebar: {
    color: colors.black,
    fontWeight: "bold",
    margin: 10,
    fontSize: 14,
    textTransform: "uppercase",
  },
  timebarContainer: {
    backgroundColor: colors.light,
    flexDirection: "row",
    justifyContent: "center",
  },
  pressed: {
    backgroundColor: colors.medium,
    color: colors.light,
  },
});

export default ChartInterfaceScreenWMenu;
