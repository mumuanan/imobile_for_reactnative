/*********************************************************************************
 Copyright © SuperMap. All rights reserved.
 Author: tronzzb
 Description: AR户型图采集
 **********************************************************************************/
import {
  NativeModules,
  Platform,
} from "react-native"
let SMeasureView = NativeModules.SMeasureView

addNewRecord = () => {
  try {
    return SMeasureView.addNewRecord()
  } catch (error) {
    console.error(error)
  }
}

undoDraw = () => {
  try {
    return SMeasureView.undoDraw()
  } catch (error) {
    console.error(error)
  }
}

clearAll = () => {
  try {
    return SMeasureView.clearAll()
  } catch (error) {
    console.error(error)
  }
}

setEnableSupport = (value) => {
  try {
    return SMeasureView.setEnableSupport(value)
  } catch (error) {
    console.error(error)
  }
}

isSupportedARCore = () => {
  try {
    return SMeasureView.isSupportedARCore()
  } catch (error) {
    console.error(error)
  }
}

saveDataset = () => {
  try {
    return SMeasureView.saveDataset()
  } catch (error) {
    console.error(error)
  }
}

initMeasureCollector = (datasourceName, datasetName) => {
  try {
    return SMeasureView.initMeasureCollector(datasourceName, datasetName)
  } catch (error) {
    console.error(error)
  }
}

setFlagType = type => {
  try {
    return SMeasureView.setFlagType(type)
  } catch (error) {
    console.error(error)
  }
}

export default {
  addNewRecord,
  undoDraw,
  clearAll,
  setEnableSupport,
  isSupportedARCore,
  saveDataset,
  initMeasureCollector,
  setFlagType,
}