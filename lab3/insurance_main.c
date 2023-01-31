#include <stdio.h>
#include </courses/TDDD04/symbolic/include/klee/klee.h>

/*
 * Calculate the deductible for the client
 * Base cost is 5000 SEK if the client is above 30 or has had a driving licence for more that 5 years and 8000 SEK otherwise
 * With every accident for that calendar the deductible increases:
 * 1 accident 	: by 1000 SEK
 * 2 accidents  : by 2500 SEK
 * 3 accidents	: by 4000 SEK
 * 4 accidents and more by : 10000 SEK
 * If the client if a gold member, then for the first 2 accidents, there is no increase
 * but for 3 accidents and more normal rates apply
 */

struct Client
{
    int clientAge;
    int yearOfLicence;
    int numberOfAccidents;
    int isGoldMember;
};

int getClientDeductible(struct Client client)
{
    int base_cost = 8000;
    if (client.numberOfAccidents < 0)
    {
        return -1;
    }
    if (client.clientAge < 18 || client.clientAge > 100)
    {
        return -1;
    }
    if (client.yearOfLicence > client.clientAge - 18 || client.yearOfLicence < 0)
    {
        return -1;
    }
    if (client.clientAge >= 30 || client.yearOfLicence >= 5)
    {
        base_cost = 5000;
    }

    switch (client.numberOfAccidents) {
    case 0:
                return base_cost;

        case 1:
            if (client.isGoldMember)
                return base_cost;
            return base_cost + 1000;

        case 2:
            if (client.isGoldMember)
                return base_cost;
                return base_cost + 2500;

        case 3:
            return base_cost + 4000;

        default:
            return base_cost + 10000;

        }

    return -1;
}

int main()
{
    struct Client client;

    

    int result = 0;
    klee_make_symbolic(&client, sizeof(client), "client");
    result = getClientDeductible(client);
    //invalid inputs

    
    printf("Result was %d\n", result);
    klee_assert(result == -1 || (result <= 18000 && result >= 5000));
    if(client.clientAge < 18) {
    klee_assert(result == -1);
    }
    
    result = getClientDeductible(client);

    
    
    
    
    return result;
}

