export function phoneValidator (rule: any, value: any, callback: any, name: any) {
    if (value) {
        const reg1 = new RegExp('^[0+][0-9]+$', 'g')
        if (reg1.test(value) === false) {
            callback(new Error(name))
        } else {
            if (value.toString().trim().length > 9 && value.toString().trim().length < 16) {
                callback()
            } else {
                callback(new Error(name))
            }
        }
    }
    callback()
}


export function validateEmailInvalid (rule: any, value: any, callback: any, t: any, name: any) {
    if (value) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(value)) {
            callback(new Error(t('validate.regex', {name: name})))
        } else {
            callback()
        }
    }
    callback()
}
