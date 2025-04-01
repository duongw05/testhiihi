import request from '@/utils/system/request'
import {api} from "@/api/index";

export function getData(data: object) {
  return request({
    url: api.searchAllSystemValues,
    method: 'get',
    data
  })
}