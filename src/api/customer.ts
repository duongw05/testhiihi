import request from '@/utils/system/request'

const prefix = 'customer/client/api/v1/customer';
export function searchCustomer(data: object, pagination: any) {
  return request({
    url: prefix + `/search?page=${pagination.current}&size=${pagination.pageSize}`,
    method: 'post',
    data
  })
}

export function delCustomer(id: any) {
  return request({
    url: `${prefix}/delete/${id}`,
    method: 'delete',
  });
}

export function exportCustomer(data : object) {
  return request({
    url: prefix + `/export`,
    method: 'post',
    data,
    responseType: 'blob'
  })
}

export function addCustomer(data: object) {
  return request({
    url: `${prefix}/add`,
    method: 'post',
    data
  })
}
export function updateCustomer(data: object) {
  return request({
    url: `${prefix}/update`,
    method: 'post',
    data
  })
}

export function getCustomerDetail(id: number) {
  return request({
    url: `${prefix}/detail/${id}`,
    method: 'get',
  })
}