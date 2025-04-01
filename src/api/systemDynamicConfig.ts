import request from '@/utils/system/request'
import {api} from "@/api/index";
const prefix = 'business/client/api/v1/config-dynamic/'

export function getDataConfigDynamic(data: object) {
  return request({
    url: prefix + 'fetchConfig',
    method: 'post',
    data
  })
}